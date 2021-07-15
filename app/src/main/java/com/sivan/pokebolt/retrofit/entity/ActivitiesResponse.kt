package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ActivitiesResponse(

    @SerializedName("friends")
    @Expose
    var friends : List<Friend>,

    @SerializedName("foes")
    @Expose
    var foes : List<Foe>

    ) {

data class Foe(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("pokemon")
    @Expose
    val pokemon: Pokemon
)

data class Friend(

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("pokemon")
    @Expose
    val pokemon: Pokemon
)

data class Pokemon(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("captured_at")
    @Expose
    val captured_at: String
)

}