package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoveInfo(
    @SerializedName("power")
    @Expose
    val power : Int,

    @SerializedName("pp")
    @Expose
    val pp : Int
) {

}
