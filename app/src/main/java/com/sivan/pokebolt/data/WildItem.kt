package com.sivan.pokebolt.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.database.entities.toModel
import com.sivan.pokebolt.retrofit.entity.*
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.time.ZoneId
import java.time.ZonedDateTime

@Parcelize
data class WildItem(
    var id : Int,
    var name: String,
    var url: String,
    var latitude: Float,
    var longitude: Float
    ) : Parcelable {
}
