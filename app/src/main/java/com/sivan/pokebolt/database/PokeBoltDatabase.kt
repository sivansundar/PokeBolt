package com.sivan.pokebolt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sivan.pokebolt.database.entities.StatsCacheEntity

@Database(entities = [StatsCacheEntity::class], version = 1, exportSchema = false)
abstract class PokeBoltDatabase : RoomDatabase() {

    companion object {
        val DATABASE_NAME : String = "pokebolt_db"
    }
}