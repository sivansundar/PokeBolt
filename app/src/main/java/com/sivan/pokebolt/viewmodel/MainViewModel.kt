package com.sivan.pokebolt.viewmodel

import androidx.lifecycle.*
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.repository.MainRepository
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import com.sivan.pokebolt.retrofit.entity.MyTeamEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(){

    suspend fun getActivities(): Flow<DataState<ActivitiesResponse>> {
        return repository.getActivities()

    }

    suspend fun getMyTeam(): Flow<DataState<Boolean>> {
        return repository.getTeam()
    }


    suspend fun getMyTeamFromDB(): Flow<List<MyTeamCacheEntity>> {
        return repository.getTeamFromDB()
    }

    suspend fun getPokemon(id : String){
        repository.getPokemon(id)
    }

}