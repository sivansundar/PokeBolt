package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TokenResponse(

    @SerializedName("token")
    @Expose
    var token : String,

    @SerializedName("expiresAt")
    @Expose
    var expiresAt : Long,


) {
}