package com.sivan.pokebolt.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sivan.pokebolt.retrofit.AuthInterceptor
import com.sivan.pokebolt.retrofit.PokeBoltInterface
import com.sivan.pokebolt.retrofit.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder() : Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson : Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://us-central1-samaritan-android-assignment.cloudfunctions.net")
            .addConverterFactory(GsonConverterFactory.create(gson))

    }


    @Singleton
    @Provides
    fun providePokeBoltService(retrofit: Retrofit.Builder, @ApplicationContext context : Context) : PokeBoltInterface {

        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.tag("OkHttp").d(it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

        return retrofit.client(
            OkHttpClient.Builder()

                .callTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(AuthInterceptor(context))
                .authenticator(TokenAuthenticator(context))
                .build()
        )
            .build().create(PokeBoltInterface::class.java)
    }
}