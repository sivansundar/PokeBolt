package com.sivan.pokebolt.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sivan.pokebolt.retrofit.entity.Moves
import com.sivan.pokebolt.retrofit.entity.Sprites
import com.sivan.pokebolt.retrofit.entity.Stats
import com.sivan.pokebolt.retrofit.entity.Types
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamItem(

    val id: Int,

    val name: String,

    var stats: List<Stats>,

    var sprites: Sprites,

    var types: List<Types>,

    var moves: List<Moves>,

    val captured_at: String,

    val captured_lat_at: Double,

    val captured_long_at: Double,
) : Parcelable{
}