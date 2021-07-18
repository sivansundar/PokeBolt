package com.sivan.pokebolt.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats")
data class StatsCacheEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "pokemon_id")
    var pokemonId : Int,

    @ColumnInfo(name = "hp")
    var hp : Int,

    @ColumnInfo(name = "type")
    var type : String,

    @ColumnInfo(name = "moves")
    var moves : String,

    @ColumnInfo(name = "level")
    var level : Int,

    @ColumnInfo(name = "front_image_url")
    var front_image_url : String,

    @ColumnInfo(name = "back_image_url")
    var back_image_url : String,

    ) {
}