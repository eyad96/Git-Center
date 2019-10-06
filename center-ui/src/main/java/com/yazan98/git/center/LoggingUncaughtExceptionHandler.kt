package com.yazan98.git.center

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:00 PM
 */


import android.annotation.TargetApi
import android.os.Build
import android.os.Process
import timber.log.Timber
import javax.inject.Inject

@TargetApi(Build.VERSION_CODES.O)
class LoggingUncaughtExceptionHandler @Inject constructor(private val defaultHandler: Thread.UncaughtExceptionHandler) :
    Thread.UncaughtExceptionHandler {

    private var crashing: Boolean = false

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        try {
            // Don't re-enter -- avoid infinite loops if crash-reporting crashes.
            if (crashing) {
                return
            }
            crashing = true
            Timber.tag("Dank")
                .e(e, "FATAL EXCEPTION: %s\nPID: %s", t!!.name, Process.myPid())
        } finally {
            defaultHandler.uncaughtException(t, e)
        }
    }
}