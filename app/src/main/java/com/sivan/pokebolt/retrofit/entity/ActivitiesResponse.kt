package com.sivan.pokebolt.retrofit.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class ActivitiesResponse(

    @SerializedName("friends")
    @Expose
    var friends : List<FFObject>,

    @SerializedName("foes")
    @Expose
    var foes : List<FFObject>

    ) : Parcelable{

}

@Parcelize
data class FFObject(

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("pokemon")
    @Expose
    val pokemon: Pokemon,


) : Parcelable{}

@Parcelize
data class Pokemon(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    var sprites: Sprites,

    @SerializedName("captured_at")
    @Expose
    val captured_at: String
) : Parcelable {}