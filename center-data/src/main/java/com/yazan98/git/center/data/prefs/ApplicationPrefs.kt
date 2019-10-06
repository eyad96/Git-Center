package com.yazan98.git.center.data.prefs

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:13 PM
 */

import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApplicationPrefs {

    suspend fun getSelectedLanguage(): String {
        return withContext(Dispatchers.IO) {
            Prefs.getString("language", "en")
        }
    }

    suspend fun saveSelectedLanguage(language: String) {
        withContext(Dispatchers.IO) {
            Prefs.putString("language", language)
        }
    }

    suspend fun saveUserStatus(status: Boolean) {
        withContext(Dispatchers.IO) {
            Prefs.putBoolean("UserStatus", status)
        }
    }

    suspend fun getUserStatus(): Boolean {
        return withContext(Dispatchers.IO) {
            Prefs.getBoolean("UserStatus", false)
        }
    }

    suspend fun getAccessToken(): String {
        return withContext(Dispatchers.IO) {
            Prefs.getString("AccessToken", "")
        }
    }

    suspend fun saveAccessToken(token: String) {
        withContext(Dispatchers.IO) {
            Prefs.putString("AccessToken", token)
        }
    }

}