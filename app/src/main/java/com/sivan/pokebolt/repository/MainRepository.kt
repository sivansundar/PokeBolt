package com.sivan.pokebolt.repository

import androidx.lifecycle.liveData
import com.sivan.pokebolt.database.PokeBoltDatabase
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.PokeBoltInterface
import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import com.sivan.pokebolt.retrofit.entity.PokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokeBoltInterface: PokeBoltInterface,
) {


    fun getActivities() = flow {
        emit(DataState.Loading)
       try {
           val list = pokeBoltInterface.getActivities()

           for (item in list.friends) {
               val image = getPokemon("${item.pokemon.id}")
               item.pokemon.sprites = image!!.sprites
           }

           for (item in list.foes) {
               val image = getPokemon("${item.pokemon.id}")
               item.pokemon.sprites = image!!.sprites
           }

           Timber.d( "getActivities: ${list.friends} ")
            emit(DataState.Success(list))
       } catch (e: Exception) {
           Timber.d( "getActivities: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
           DataState.Error(e)
       }

   }


    suspend fun getPokemon(id : String): PokemonResponse? {
        return try {
            val list = pokeBoltInterface.getPokemon("https://pokeapi.co/api/v2/pokemon/${id}")
            list

        } catch (e: Exception) {
            Timber.d( "getPokemon: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            return null
        }
    }
}