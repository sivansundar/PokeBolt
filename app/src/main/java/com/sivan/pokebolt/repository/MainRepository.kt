package com.sivan.pokebolt.repository

import com.sivan.pokebolt.database.dao.MyTeamDao
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.PokeBoltInterface
import com.sivan.pokebolt.retrofit.entity.PokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokeBoltInterface: PokeBoltInterface,
    private val myTeamDao: MyTeamDao
) {


    fun getActivities() = flow {
        emit(DataState.Loading)
       try {
           val list = pokeBoltInterface.getActivities()

           for (item in list.friends) {
               val response = getPokemon("${item.pokemon.id}")
               item.pokemon.sprites = response!!.sprites
               item.pokemon.types = response.types
               item.pokemon.moves = response.moves
           }

           for (item in list.foes) {
               val response = getPokemon("${item.pokemon.id}")
               item.pokemon.sprites = response!!.sprites
               item.pokemon.types = response.types
               item.pokemon.moves = response.moves
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
            val pokemonResponse = pokeBoltInterface.getPokemon("https://pokeapi.co/api/v2/pokemon/${id}")
            pokemonResponse

        } catch (e: Exception) {
            Timber.d( "getPokemon: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            return null
        }
    }

    suspend fun getTeam() = flow{
        emit(DataState.Loading)

        try {
            val teamList = pokeBoltInterface.getMyTeam()
            for (item in teamList) {
                val pokemon = getPokemon(item.id.toString())
                if (pokemon != null) {
                    item.stats = pokemon.stats
                    item.sprites = pokemon.sprites
                    item.types = pokemon.types


                    val myTeamCacheEntity = MyTeamCacheEntity(
                        pokemonId = item.id,
                        name = item.name,
                        sprites = Json.encodeToString(item.sprites),
                        stats = Json.encodeToString(item.stats),
                        types = Json.encodeToString(item.types),
                        moves = Json.encodeToString(pokemon.moves),
                        captured_at = item.captured_at,
                        captured_lat_at = item.captured_lat_at,
                        captured_long_at = item.captured_long_at
                    )

                    if (myTeamDao.exists(item.id)) {
                        Timber.d("Insert status : Pokemon exists with ID : ${item.id}")
                    } else {
                        val insertStatus = myTeamDao.insert(myTeamCacheEntity)
                        Timber.d("Insert status : $insertStatus")
                    }
                }
            }

            emit(DataState.Success(true))
        }catch (e : Exception) {
            Timber.d( "getTeam: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            emit(DataState.Error(e))

        }
    }

    suspend fun getTeamFromDB(): Flow<List<MyTeamCacheEntity>> {
            return myTeamDao.getTeamList()
    }
}