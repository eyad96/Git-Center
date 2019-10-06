package com.yazan98.git.center.data

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:11 PM
 */


import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun getRetrofit(): Retrofit
}