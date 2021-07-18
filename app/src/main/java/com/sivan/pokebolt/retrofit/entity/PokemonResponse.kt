package com.sivan.pokebolt.retrofit.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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

    )

data class Moves(
    @SerializedName("move")
    @Expose
    val move : Move
) {}

data class Move(
    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("url")
    @Expose
    val url : String,

) {}

data class Types(
    @SerializedName("slots")
    @Expose
    val slot: Int,

    @SerializedName("type")
    @Expose
    val type: Type
) {}

data class Type(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("url")
    @Expose
    val url: String
) {}


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
){}

data class Stat(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("url")
    @Expose
    val url: String
)

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
) : Parcelable

@Parcelize
data class Other(
    @SerializedName("dream_world")
    @Expose
    val dream_world : DreamWorld,

    @SerializedName("official-artwork")
    @Expose
    val official_artwork : OfficialArtwork
) : Parcelable {}

@Parcelize
data class DreamWorld(
    @SerializedName("front_default")
    @Expose
    val front_default : String
) : Parcelable{}

@Parcelize
data class OfficialArtwork(
    @SerializedName("front_default")
    @Expose
    val front_default : String
) : Parcelable {}

