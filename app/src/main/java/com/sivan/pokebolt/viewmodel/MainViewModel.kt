package com.sivan.pokebolt.viewmodel

import androidx.lifecycle.*
import com.sivan.pokebolt.repository.MainRepository
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
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

    suspend fun getPokemon(id : String){
        repository.getPokemon(id)
    }

}