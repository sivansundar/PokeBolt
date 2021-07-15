package com.sivan.pokebolt.retrofit

import android.provider.ContactsContract
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import com.sivan.pokebolt.retrofit.entity.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeBoltInterface {

    @POST("/token?email=sivan.sundar@gmail.com")
    fun getToken(): Call<TokenResponse>

    @GET("/activity")
    suspend fun getActivities() : ActivitiesResponse
}