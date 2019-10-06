package com.yazan98.git.center.data

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:11 PM
 */


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    internal fun provideOkhttpClient(
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val request =
                chain.request()
                    .newBuilder()
                    .build()
            chain.proceed(request)
        }
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    }

}
