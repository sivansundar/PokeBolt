package com.sivan.pokebolt.data

import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.retrofit.entity.WildEntity
import com.sivan.pokebolt.retrofit.entity.WildPokemon
import java.time.ZoneId
import java.time.ZonedDateTime

data class PostPokemonItem(

    var id : Int,


    var name: String,


    var url: String,


    var latitude: Float,


    var longitude: Float,

    var types: String,

    var moves: String,

    var stats: String,

    var sprites: String,
) {
}

fun PostPokemonItem.toTeamCacheEntity(): MyTeamCacheEntity {
    return MyTeamCacheEntity(
        pokemonId = this.id.toInt(),
        name = this.name,
        sprites = this.sprites,
        stats = this.stats,
        types = this.types,
        moves = this.moves,
        captured_lat_at = this.latitude,
        captured_long_at = this.longitude,
        captured_at = ZonedDateTime.now(ZoneId.systemDefault()).toString(),
    )
}

fun PostPokemonItem.toCapturedCacheEntity() : CapturedCacheEntity {
    return CapturedCacheEntity(
        pokemonId = this.id,
        name = this.name,
        sprites = this.sprites,
        stats = this.stats,
        types = this.types,
        moves = this.moves,
        captured_lat_at = this.latitude,
        captured_long_at = this.longitude,
        captured_at = ZonedDateTime.now(ZoneId.systemDefault()).toString(),
    )
}

fun PostPokemonItem.toEntity(): WildEntity {
    return WildEntity(
        pokemon = WildPokemon(
            id = this.id,
            name = this.name,
            //url = this.url,
            latitude = this.latitude,
            longitude = this.longitude
        )
    )
}