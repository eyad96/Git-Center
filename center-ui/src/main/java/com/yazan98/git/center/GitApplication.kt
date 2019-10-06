package com.yazan98.git.center

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Handler
import android.os.StrictMode
import com.pixplicity.easyprefs.library.Prefs
import com.yazan98.git.center.data.ErrorResolver
import com.yazan98.git.center.data.prefs.ApplicationPrefs
import com.yazan98.git.center.di.ApplicationComponent
import com.yazan98.git.center.di.ApplicationModule
import com.yazan98.git.center.di.DaggerApplicationComponent
import io.atto.utils.Atto
import io.atto.utils.Atto.startStrictMode
import io.atto.utils.AttoApplication
import io.atto.utils.ImageLoader
import io.atto.utils.utils.AttoLocaleManager
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.io.InterruptedIOException
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 12:54 PM
 */

class GitApplication @Inject constructor() : AttoApplication(),
    Thread.UncaughtExceptionHandler {

    private val errorResolver: ErrorResolver by lazy { ErrorResolver() }
    val applicationComponent: ApplicationComponent by lazy {
        getAppComponent(applicationContext, this)
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler(undeliveredExceptionsHandler())

        Atto
            .addApplication(this)
            .addApplicationStatus(BuildConfig.DEBUG)
            .registerCompatVector()
            .registerExceptionHandler(this)
            .startCrashlytice(this)
            .startFirebase(this)
            .startImageLoader(ImageLoader.FRESCO, this)
            .startImageLoader(ImageLoader.PICASSO, this)
            .startTimber(this)

        if (SDK_INT >= 16) {
            //restore strict mode after onCreate() returns.
            Handler().postAtFrontOfQueue {
                if (BuildConfig.DEBUG) {
                    startStrictMode()
                }
            }
        }

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        applicationComponent.inject(this)

        if (SDK_INT >= Build.VERSION_CODES.O) {
            Thread.setDefaultUncaughtExceptionHandler(LoggingUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()))
        }

    }

    private fun startStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        AlertDialog.Builder(applicationContext)
            .setTitle(getString(R.string.unexpected_error_title))
            .setMessage(e?.message)
            .setCancelable(true)
            .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        GlobalScope.launch {
            AttoLocaleManager.setLocale(
                applicationContext,
                ApplicationPrefs.getSelectedLanguage()
            )
        }
    }

    companion object {
        @JvmStatic
        fun getAppComponent(context: Context, app: Application): ApplicationComponent {
            return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(context, app))
                .build()
        }
    }

    private fun undeliveredExceptionsHandler(): Consumer<Throwable> {
        return Consumer {
            val result = errorResolver.findActualCause(it)

            if (result is OnErrorNotImplementedException) {
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), result)
            }

            if (result is IOException) {
                Timber.w("IOException")
                result.printStackTrace()
            }
            if (result is InterruptedException || result.cause is InterruptedIOException) {
                Timber.w("Interrupted exception")

            }
            if (result is NullPointerException || result is IllegalArgumentException) {
                Timber.e(result, "Undeliverable exception")
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), result)
            }
            if (result is IllegalStateException) {
                Timber.e(result, "Undeliverable exception")
                Thread.currentThread()
                    .uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), result)

            }

            Timber.e(result, "Undeliverable exception received, not sure what to do.")
        }
    }

}