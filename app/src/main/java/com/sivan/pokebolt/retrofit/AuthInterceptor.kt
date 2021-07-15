package com.sivan.pokebolt.retrofit

import android.content.Context
import com.google.gson.Gson
import com.sivan.pokebolt.retrofit.entity.TokenResponse
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        //Log.d("TAG", "intercept: Token : ")
        // If token has been saved, add it to the request

        Timber.d("Here")
        sessionManager.fetchAuthToken()?.let {

            Timber.d("Token is $it")
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}