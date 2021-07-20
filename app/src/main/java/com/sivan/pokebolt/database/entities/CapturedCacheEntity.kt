package com.sivan.pokebolt.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sivan.pokebolt.data.CapturedItem
import com.sivan.pokebolt.data.TeamItem
import com.sivan.pokebolt.retrofit.entity.Moves
import com.sivan.pokebolt.retrofit.entity.Sprites
import com.sivan.pokebolt.retrofit.entity.Stats
import com.sivan.pokebolt.retrofit.entity.Types
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Entity(tableName = "captured")
data class CapturedCacheEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "pokemon_id")
    var pokemonId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "captured_at")
    var captured_at: String,

    @ColumnInfo(name = "types")
    var types: String,

    @ColumnInfo(name = "moves")
    var moves: String,

    @ColumnInfo(name ="captured_lat_at")
    val captured_lat_at: Double,

    @ColumnInfo(name ="captured_long_at")
    val captured_long_at: Double,

    @ColumnInfo(name = "stats")
    var stats: String,

    @ColumnInfo(name = "sprites")
    var sprites: String,

    ) {
}


fun CapturedCacheEntity.toModel() : CapturedItem {
    return CapturedItem(
        id = this.id,
        pokemonId = this.pokemonId,
        name = this.name,
        sprites = Json.decodeFromString(Sprites.serializer(), this.sprites),
        stats = Json.decodeFromString(ListSerializer(Stats.serializer()), this.stats),
        types = Json.decodeFromString(ListSerializer(Types.serializer()), this.types),
        moves = Json.decodeFromString(ListSerializer(Moves.serializer()), this.moves),
        captured_lat_at = this.captured_lat_at,
        captured_long_at = this.captured_long_at,
        captured_at = this.captured_at

    )
}

fun List<CapturedCacheEntity>.toListModel() : List<CapturedItem> {
    return this.map { it.toModel() }
}