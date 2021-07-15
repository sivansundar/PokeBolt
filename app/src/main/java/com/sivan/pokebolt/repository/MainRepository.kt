package com.sivan.pokebolt.repository

import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.PokeBoltInterface
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokeBoltInterface: PokeBoltInterface
) {

   suspend fun getActivities() {

       try {
           val list = pokeBoltInterface.getActivities()
           Timber.d( "getActivities: ${list} ")

       } catch (e: Exception) {
           Timber.d( "getActivities: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
           DataState.Error(e)
       }
    }
}