package com.yazan98.git.center.di

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 12:59 PM
 */


import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val applicationContext: Context , private val app: Application) {

    @Provides
    fun provideApplicationContext(): Context = applicationContext

    @Provides
    @Singleton
    fun getApplicationClass(): Application  = app

}
