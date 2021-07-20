package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("count")
    @Expose
    val count: Int,

    @SerializedName("next")
    @Expose
    val next: String,

    @SerializedName("previous")
    @Expose
    val previous: String,

    @SerializedName("results")
    @Expose
    val results: List<Result>
)

data class Result(

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("url")
    @Expose
    val url: String
)