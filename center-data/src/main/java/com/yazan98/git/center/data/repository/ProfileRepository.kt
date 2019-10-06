package com.yazan98.git.center.data.repository

import com.yazan98.git.center.data.BuildConfig
import com.yazan98.git.center.data.GithubBaseRepository
import com.yazan98.git.center.data.api.ProfileApi
import com.yazan98.git.center.domain.models.User
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:59 PM
 */

class ProfileRepository @Inject constructor() : GithubBaseRepository<ProfileApi>() {

    public override fun getService(): ProfileApi {
        return getApiProvider().create(ProfileApi::class.java)
    }

    suspend fun login(username: String, password: String): Single<User> {
        return withContext(Dispatchers.IO) {
            getService().login(
                username,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                getBasicAuthToken(username, password)
            )
        }
    }
}