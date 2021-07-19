package com.sivan.pokebolt.retrofit.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CapturedEntity(
    @SerializedName("captured")
    @Expose
    val captured : List<CapturedEntity>
) {
}