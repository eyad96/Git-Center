package com.yazan98.git.center.data

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:13 PM
 */

import androidx.annotation.StringRes
import com.google.auto.value.AutoValue
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.UndeliverableException
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.StreamResetException
import retrofit2.HttpException
import java.io.FileNotFoundException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class ErrorResolver @Inject constructor() {

    fun resolve(error: Throwable?): ResolvedError {
        if (error == null) {
            return ResolvedError.create(
                ResolvedError.Type.UNKNOWN,
                R.string.common_unknown_error_emoji,
                R.string.common_unknown_error_message
            )
        }

        val actualError = findActualCause(error)

        return if (isNetworkTimeoutError(actualError)) {
            if (actualError is UnknownHostException
                && actualError.message != null
                && (actualError.message!!.contains("test.com") || actualError.message!!.contains("test:8080/"))
            ) {
                ResolvedError.create(
                    ResolvedError.Type.NETWORK_ERROR,
                    R.string.common_network_error_emoji,
                    R.string.common_network_error_with_reddit_message
                )

            } else {
                ResolvedError.create(
                    ResolvedError.Type.NETWORK_ERROR,
                    R.string.common_network_error_emoji,
                    R.string.common_network_error_with_other_websites_message
                )
            }

        } else if (actualError is HttpException && actualError.code() == 503) {
            ResolvedError.create(
                ResolvedError.Type.REDDIT_IS_DOWN,
                R.string.common_reddit_is_down_error_emoji,
                R.string.common_reddit_is_down_error_message
            )

        } else if (actualError is CancellationException || actualError is InterruptedIOException || actualError is InterruptedException) {
            ResolvedError.create(
                ResolvedError.Type.CANCELATION,
                R.string.common_error_cancelation_emoji,
                R.string.common_error_cancelation_message
            )

        } else if (actualError is IllegalStateException && actualError.message?.contains("no active user context") == true) {
            ResolvedError.create(
                ResolvedError.Type.KNOWN_BUT_IGNORED,
                R.string.common_error_known_jraw_emoji,
                R.string.common_error_known_jraw_message
            )

        } else if (actualError is FileNotFoundException && actualError.message?.contains("No content provider") == true) {
            ResolvedError.create(
                ResolvedError.Type.KNOWN_BUT_IGNORED,
                R.string.common_error_cancelation_emoji,
                R.string.common_error_cancelation_message
            )

        } else {
            ResolvedError.create(
                ResolvedError.Type.UNKNOWN,
                R.string.common_unknown_error_emoji,
                R.string.common_unknown_error_message
            )
        }
    }

    fun findActualCause(error: Throwable): Throwable {
        var actualError = error

        if (actualError is ExecutionException) {
            // AFAIK, thrown by Glide in situations like socket-timeout.
            actualError = findActualCause(actualError.cause!!)
        }
        if (actualError is CompositeException) {
            actualError = findActualCause(actualError.cause)
        }
        if (actualError is UndeliverableException) {
            actualError = findActualCause(actualError.cause!!)
        }
        if (actualError is RuntimeException && actualError.cause != null && isNetworkTimeoutError(
                actualError.cause
            )
        ) {
            // Stupid JRAW wraps all HTTP exceptions with RuntimeException.
            // Update: this may no longer be true with JRAW v1.0 (Dank v.0.6.1).
            actualError = findActualCause(actualError.cause!!)
        }
        if (actualError is IllegalStateException && actualError.message?.contains("Reached retry limit") == true) {
            actualError = findActualCause(actualError.cause!!)
        }
        return actualError
    }

    private fun isNetworkTimeoutError(error: Throwable?): Boolean {
        return (error is SocketException
                || error is SocketTimeoutException
                || error is UnknownHostException
                || error is StreamResetException
                || error is ConnectionShutdownException
                )
    }
}


@AutoValue
abstract class ResolvedError {

    val isUnknown: Boolean
        get() = type() == Type.UNKNOWN

    val isNetworkError: Boolean
        get() = type() == Type.NETWORK_ERROR

    val isRedditServerError: Boolean
        get() = type() == Type.REDDIT_IS_DOWN

    val isImgurRateLimitError: Boolean
        get() = type() == Type.IMGUR_RATE_LIMIT_REACHED

    enum class Type {
        UNKNOWN,
        KNOWN_BUT_IGNORED,
        NETWORK_ERROR,
        REDDIT_IS_DOWN,
        IMGUR_RATE_LIMIT_REACHED,
        CANCELATION
    }

    abstract fun type(): Type

    // FIXME: rename to emojiRes().
    @StringRes
    abstract fun errorEmojiRes(): Int

    // FIXME: rename to messageRes().
    @StringRes
    abstract fun errorMessageRes(): Int

    fun ifUnknown(runnable: Runnable) {
        if (isUnknown) {
            runnable.run()
        }
    }

    companion object {

        fun create(type: Type, @StringRes errorEmoji: Int, @StringRes errorMessage: Int): ResolvedError {
            return AutoValue_ResolvedError(type, errorEmoji, errorMessage)
        }
    }
}