package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CaptureWildPokemonResponse(
    @SerializedName("success")
    @Expose
    var message : String
) {
}