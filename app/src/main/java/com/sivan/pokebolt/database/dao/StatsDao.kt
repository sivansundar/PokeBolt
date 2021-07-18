package com.sivan.pokebolt.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.sivan.pokebolt.database.entities.StatsCacheEntity

@Dao
interface StatsDao {

    @Insert
    fun insert(statsCacheEntity: StatsCacheEntity)
}