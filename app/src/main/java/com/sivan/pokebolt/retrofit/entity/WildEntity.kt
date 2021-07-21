package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WildEntity(

    @SerializedName("pokemon")
    @Expose
    var pokemon : WildPokemon

    ) {
}

data class WildPokemon(
    @SerializedName("id")
    @Expose
    var id : Int,

    @SerializedName("name")
    @Expose
    var name: String,

//    @SerializedName("url")
//    @Expose
//    var url: String,

    @SerializedName("lat")
    @Expose
    var latitude: Float,

    @SerializedName("long")
    @Expose
    var longitude: Float,
){}