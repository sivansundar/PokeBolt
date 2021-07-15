package com.sivan.pokebolt.database

import androidx.room.RoomDatabase

abstract class PokeBoltDatabase : RoomDatabase() {

    companion object {
        val DATABASE_NAME : String = "pokebolt_db"
    }
}