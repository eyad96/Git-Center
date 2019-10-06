package com.yazan98.git.center.data.api

import com.yazan98.git.center.domain.models.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 4:51 PM
 */

interface ProfileApi {

    @GET("/users/{username}")
    fun login(
        @Path("{username}") username: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecrete: String,
        @Header("Authorization") authKey: String
    ): Single<User>

}
