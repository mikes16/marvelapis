package com.interview.kotlin.iterview.core.di.module

import com.interview.kotlin.iterview.BuildConfig
import com.interview.kotlin.iterview.feature.characters.domain.repository.CharactersRepository
import com.interview.kotlin.iterview.feature.characters.domain.repository.ComicsRepository
import dagger.Module

import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

import java.util.concurrent.TimeUnit


@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {

        val okHttpClientBuilder = OkHttpClient.Builder()

        okHttpClientBuilder.addInterceptor {
            val url = it.request().url().newBuilder()
                    .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
                    .addQueryParameter("ts", BuildConfig.TIMESTAMP)
                    .addQueryParameter("hash", BuildConfig.HASH).build()
            val request = it.request().newBuilder().url(url).build()
            it.proceed(request)
        }

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        val okHttpClient = okHttpClientBuilder
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideCharactersRepository(dataSource: CharactersRepository.Network): CharactersRepository = dataSource

    @Provides
    @Reusable
    @JvmStatic
    fun provideComicsRepository(dataSource: ComicsRepository.Network): ComicsRepository = dataSource

    @Provides
    @Reusable
    @JvmStatic
    fun provideNetworkExecutor(): Executor = Executors.newFixedThreadPool(5)

}
