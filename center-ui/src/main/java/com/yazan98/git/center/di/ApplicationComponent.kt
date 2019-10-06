package com.yazan98.git.center.di

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 12:59 PM
 */

import android.app.Application
import android.content.Context
import com.yazan98.git.center.GitApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: GitApplication)

    fun applicationContext(): Context

    fun getApplication(): Application

}
