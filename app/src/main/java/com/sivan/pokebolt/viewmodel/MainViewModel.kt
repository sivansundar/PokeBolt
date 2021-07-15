package com.sivan.pokebolt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sivan.pokebolt.repository.MainRepository
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(){

    private val _activities = MutableLiveData<DataState<List<ActivitiesResponse>>>()
    val activities : LiveData<DataState<List<ActivitiesResponse>>> = _activities

    suspend fun getActivities() {
        repository.getActivities()
    }

}