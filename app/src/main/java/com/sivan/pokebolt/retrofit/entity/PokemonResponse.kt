package com.sivan.pokebolt.retrofit.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PokemonResponse(
    @SerializedName("stats")
    @Expose
    val stats : List<Stats>,

    @SerializedName("sprites")
    @Expose
    val sprites: Sprites,

    @SerializedName("types")
    @Expose
    val types: List<Types>,

    @SerializedName("moves")
    @Expose
    val moves: List<Moves>,

    ) : Parcelable

@Serializable
@Parcelize
data class Moves(
    @SerializedName("move")
    @Expose
    val move : Move
) : Parcelable{}

@Serializable
@Parcelize
data class Move(
    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("url")
    @Expose
    val url : String,

) : Parcelable{}

@Serializable
@Parcelize
data class Types(
    @SerializedName("slots")
    @Expose
    val slot: Int,

    @SerializedName("type")
    @Expose
    val type: Type
) : Parcelable{}

@Serializable
@Parcelize
data class Type(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("url")
    @Expose
    val url: String
) : Parcelable {}

@Serializable
@Parcelize
data class Stats(
    @SerializedName("base_stat")
    @Expose
    val base_stat: Int,

    @SerializedName("effort")
    @Expose
    val effort: Int,

    @SerializedName("stat")
    @Expose
    val stat: Stat
) : Parcelable{}

@Serializable
@Parcelize
data class Stat(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("url")
    @Expose
    val url: String
) : Parcelable {}

@Serializable
@Parcelize
data class Sprites(
    @SerializedName("back_default")
    @Expose
    val back_default : String,

    @SerializedName("front_default")
    @Expose
    val front_default : String,

    @SerializedName("other")
    @Expose
    val other : Other
) : Parcelable {}

@Serializable
@Parcelize
data class Other(
    @SerializedName("dream_world")
    @Expose
    val dream_world : DreamWorld,

    @SerializedName("official-artwork")
    @Expose
    val official_artwork : OfficialArtwork
) : Parcelable{}

@Serializable
@Parcelize
data class DreamWorld(
    @SerializedName("front_default")
    @Expose
    val front_default : String
) : Parcelable{}

@Serializable
@Parcelize
data class OfficialArtwork(
    @SerializedName("front_default")
    @Expose
    val front_default : String
) : Parcelable{}

