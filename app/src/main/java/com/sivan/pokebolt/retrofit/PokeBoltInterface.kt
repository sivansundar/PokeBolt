package com.sivan.pokebolt.retrofit

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.sivan.pokebolt.retrofit.entity.*
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

    //My-team
    @GET("/my-team")
    suspend fun getMyTeam() : List<MyTeamEntity>

    @GET("/captured")
    suspend fun getCaptured() : List<CapturedResponse>

    @GET()
    fun getMoveDetails(@Url url : String) : MoveInfo

    @GET()
    suspend fun getPokemon(@Url url : String) : PokemonResponse


}