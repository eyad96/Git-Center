package com.yazan98.git.center.data

import io.atto.data.AttoRepository
import retrofit2.Retrofit

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:12 PM
 */

abstract class GithubBaseRepository<Api> : AttoRepository<Api>() {

    private val apiComponent: ApiComponent by lazy {
        getComponent()
    }

    protected fun getApiProvider(): Retrofit {
        return apiComponent.getRetrofit()
    }

    private fun getComponent(): ApiComponent {
        return DaggerApiComponent.builder()
            .apiModule(ApiModule())
            .build()
    }


    override fun getBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

}
