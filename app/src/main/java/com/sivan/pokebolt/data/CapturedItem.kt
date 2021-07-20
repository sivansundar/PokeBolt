package com.sivan.pokebolt.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.sivan.pokebolt.retrofit.entity.Moves
import com.sivan.pokebolt.retrofit.entity.Sprites
import com.sivan.pokebolt.retrofit.entity.Stats
import com.sivan.pokebolt.retrofit.entity.Types
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CapturedItem(

    var id: Int,

    var pokemonId: Int,

    var name: String,

    var captured_at: String,

    var stats: List<Stats>,

    var sprites: Sprites,

    var types: List<Types>,

    var moves: List<Moves>,

    var captured_lat_at: Double,

    var captured_long_at: Double,
) : Parcelable{
}