package com.sivan.pokebolt.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface CapturedDao {

    @Insert
    suspend fun insert(capturedCacheEntity: CapturedCacheEntity) : Long

    @Query("SELECT EXISTS (SELECT 1 FROM captured WHERE pokemon_id = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("SELECT * FROM captured")
    fun getCapturedList() : Observable<List<CapturedCacheEntity>>
}