package com.sivan.pokebolt.retrofit.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyTeamEntity(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    var stats: List<Stats>,

    var sprites: Sprites,

    var types: List<Types>,

    @SerializedName("captured_at")
    @Expose
    val captured_at: String,

    @SerializedName("captured_lat_at")
    @Expose
    val captured_lat_at: Double,

    @SerializedName("captured_long_at")
    @Expose
    val captured_long_at: Double,

    ) : Parcelable {
}