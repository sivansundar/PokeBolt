package com.sivan.pokebolt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sivan.pokebolt.database.dao.CapturedDao
import com.sivan.pokebolt.database.dao.MyTeamDao
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.database.entities.StatsCacheEntity
import com.sivan.pokebolt.util.DateTimeConverter

@Database(entities = [
    MyTeamCacheEntity::class, CapturedCacheEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class PokeBoltDatabase : RoomDatabase() {

    abstract fun myTeamDao() : MyTeamDao
    abstract fun capturedDao() : CapturedDao

    companion object {
        val DATABASE_NAME : String = "pokebolt_db"
    }
}