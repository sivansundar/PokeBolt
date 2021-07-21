package com.sivan.pokebolt.retrofit

import android.content.Context
import com.sivan.pokebolt.retrofit.entity.TokenResponse
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Inject


class TokenAuthenticator @Inject constructor(
    context : Context) : Authenticator {

    private val sessionManager = SessionManager(context)

    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.d("Here")
        val token = sessionManager.fetchAuthToken()
        Timber.d("Authenticator status : ${response.code}")
        getJWTToken()
        Timber.d("Authenticator authenticate : ${token}")

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${sessionManager.fetchAuthToken()}")


            .build()

//        "Accept-Encoding: gzip,deflate",
//        "Content-Type: Application/Json;charset=UTF-8",
//        "Accept: Application/Json",
//        "User-Agent: Retrofit 2.3.0"

    }

    fun getJWTToken() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokeBoltInterface = retrofit.create(PokeBoltInterface::class.java)

        val tokenRequest= pokeBoltInterface.getToken()
        val body = tokenRequest.execute().body()

        if (body != null) {
            Timber.d("Authenticator getJWTToken : body not null")

            sessionManager.saveAuthToken(body.token)
        }
        Timber.d("Authenticator getJWTToken : ${sessionManager.fetchAuthToken()}")



    }

    companion object {
        private const val BASE_URL = "https://us-central1-samaritan-android-assignment.cloudfunctions.net/"
    }
}