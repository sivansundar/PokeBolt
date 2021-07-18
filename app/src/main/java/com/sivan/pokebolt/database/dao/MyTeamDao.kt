package com.sivan.pokebolt.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyTeamDao {

    @Insert
    suspend fun insert(myTeamCacheEntity: MyTeamCacheEntity) : Long

    @Query("SELECT EXISTS (SELECT 1 FROM my_team WHERE pokemon_id = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("SELECT * FROM my_team")
    fun getTeamList() : Flow<List<MyTeamCacheEntity>>

}