package com.sivan.pokebolt.viewmodel

import androidx.lifecycle.*
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.repository.MainRepository
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
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

    suspend fun getPokemonList(): LiveData<DataState<PokemonListResponse>> {
        return repository.getPokemonList()
    }

    suspend fun getMyTeam(): Flow<DataState<Boolean>> {
        return repository.getTeam()
    }


    suspend fun getMyTeamFromDB(): Flow<List<MyTeamCacheEntity>> {
        return repository.getTeamFromDB()
    }

    suspend fun getPokemon(id : String): LiveData<DataState<PokemonResponse>> {
        return repository.getPokemon(id)
    }

    suspend fun getCapturedList(): LiveData<DataState<List<CapturedResponse>>> {
        return repository.getCapturedList()
    }

    suspend fun getCapturedListFromDB(): Observable<List<CapturedCacheEntity>> {
        return repository.getCapturedFromDB()
    }

}