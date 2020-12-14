package com.rappi.testapp.core.di

import com.rappi.testapp.BuildConfig
import com.rappi.testapp.core.data.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideInterceptors(): Array<Interceptor> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        val customHeadersInterceptor = Interceptor { chain ->
            val newUrl = chain.request().url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("laguage", "es")
                .build()

            val newRequest = chain.request().newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }

        return arrayOf(loggingInterceptor, customHeadersInterceptor)
    }

    @Provides
    fun provideOkHttpClient(httpInterceptors: Array<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                httpInterceptors.forEach { addInterceptor(it) }
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(httpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(MovieApi::class.java)
    }
}