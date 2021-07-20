package com.sivan.pokebolt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WildItem(
    var name: String,
    var url: String,
    var latitude: Double,
    var longitude: Double,

    ) : Parcelable {
}