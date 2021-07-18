package com.sivan.pokebolt.retrofit

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import com.sivan.pokebolt.retrofit.entity.MoveInfo
import com.sivan.pokebolt.retrofit.entity.PokemonResponse
import com.sivan.pokebolt.retrofit.entity.TokenResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PokeBoltInterface {

    @POST("/token?email=sivan.sundar@gmail.com")
    fun getToken(): Call<TokenResponse>

    //Get community details
    @GET("/activity")
    suspend fun getActivities() : ActivitiesResponse

    //Get community details
    @GET("/activity")
    suspend fun getCommunity() : ActivitiesResponse

    @GET()
    fun getMoveDetails(@Url url : String) : MoveInfo

    @GET()
    suspend fun getPokemon(@Url url : String) : PokemonResponse


}