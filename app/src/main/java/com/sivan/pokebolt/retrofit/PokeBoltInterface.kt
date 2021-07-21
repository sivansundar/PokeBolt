package com.sivan.pokebolt.retrofit

import com.sivan.pokebolt.retrofit.entity.*
import retrofit2.Call
import retrofit2.http.*


interface PokeBoltInterface {

    @POST("/token?email=sivan.sundar@gmail.com")
    fun getToken(): Call<TokenResponse>

    @POST("/capture")
    @Headers("Content-Type: application/json")
    suspend fun postPokemon(@Body capturedPokemonItem: WildEntity): CaptureWildPokemonResponse

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

    @GET()
    suspend fun getPokemonList(@Url url : String) : PokemonListResponse


}