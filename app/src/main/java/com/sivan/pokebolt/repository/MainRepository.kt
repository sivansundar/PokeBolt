package com.sivan.pokebolt.repository

import androidx.lifecycle.liveData
import com.sivan.pokebolt.data.PostPokemonItem
import com.sivan.pokebolt.data.toCapturedCacheEntity
import com.sivan.pokebolt.data.toEntity
import com.sivan.pokebolt.data.toTeamCacheEntity
import com.sivan.pokebolt.database.dao.CapturedDao
import com.sivan.pokebolt.database.dao.MyTeamDao
import com.sivan.pokebolt.database.entities.CapturedCacheEntity
import com.sivan.pokebolt.database.entities.MyTeamCacheEntity
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.PokeBoltInterface
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokeBoltInterface: PokeBoltInterface,
    private val myTeamDao: MyTeamDao,
    private val capturedDao: CapturedDao
) {


    suspend fun getActivities() = flow {

        /**
         * This function is responsible for performing a GET request to the /activity endpoint and retrieve the lists of pokemons captured by our friends and foes.
         * Using the URL obtained from our friends and foes list, we send a request to that URL to get additional info about the pokemon.
         *
         * */

        emit(DataState.Loading)
       try {
           val list = pokeBoltInterface.getActivities()

           for (item in list.friends) {
               val response = pokeBoltInterface.getPokemon("https://pokeapi.co/api/v2/pokemon/${item.pokemon.id}")
               item.pokemon.sprites = response.sprites
               item.pokemon.types = response.types
               item.pokemon.moves = response.moves
           }

           for (item in list.foes) {
               val response =  pokeBoltInterface.getPokemon("https://pokeapi.co/api/v2/pokemon/${item.pokemon.id}")
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

    suspend fun getCapturedList() = liveData{

        /**
         * This function is responsible for getting the list of our captured pokemon that is stored in the cloud.
         * We save this data to our Room database and later, we retrieve that data in our CapturedFragment.kt
         *
         * */

        emit(DataState.Loading)
        try {

            val capturedList = pokeBoltInterface.getCaptured()
            for (item in capturedList) {
                val pokemon = pokeBoltInterface.getPokemon("https://pokeapi.co/api/v2/pokemon/${item.id}")
                item.stats = pokemon.stats
                item.sprites = pokemon.sprites
                item.types = pokemon.types


                val capturedCacheEntity = CapturedCacheEntity(
                    pokemonId = item.id,
                    name = item.name,
                    sprites = Json.encodeToString(item.sprites),
                    stats = Json.encodeToString(item.stats),
                    types = Json.encodeToString(item.types),
                    moves = Json.encodeToString(pokemon!!.moves),
                    captured_at = item.captured_at,
                    captured_lat_at = item.captured_lat_at,
                    captured_long_at = item.captured_long_at
                )

                if (capturedDao.exists(item.id)) {
                    Timber.d("Insert status : Pokemon exists with ID : ${item.id}")
                } else {
                    val insertStatus = capturedDao.insert(capturedCacheEntity)
                    Timber.d("Insert status : $insertStatus")
                }

                emit(DataState.Success(capturedList))
            }

        }catch (e : Exception) {
            Timber.d( "getCapturedList: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            emit(DataState.Error(e))


        }
    }

    suspend fun getPokemon(id : String) = liveData{

        /**
         * This function is responsible for fetching the details of a particular pokemon
         * */

        emit(DataState.Loading)
         try {
            val pokemonResponse = pokeBoltInterface.getPokemon(id)

             emit(DataState.Success(pokemonResponse))
        } catch (e: Exception) {
            Timber.d( "getPokemon: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            emit(DataState.Error(e))
        }
    }

    suspend fun getPokemonList() = liveData{

        /**
         * This function is responsible for fetching list of pokemons from the Open Pokemon API.
         *
         * */

        emit(DataState.Loading)
        try {
            val pokemonList = pokeBoltInterface.getPokemonList("https://pokeapi.co/api/v2/pokemon/?limit=20&offset=0")
            Timber.d("Pokemon List ${pokemonList.results.size}")
            emit(DataState.Success(pokemonList))
        }catch (e : Exception) {
            Timber.d( "getPokemonList: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            emit(DataState.Error(e))

        }

    }

    suspend fun getTeam() = flow{

        /**
         * This function is responsible for fetching list of pokemons in our team.
         * We later initiate a GET request to get the details of each pokemon and store them locally before retrieving them in out MyTeamFragment.kt
         * */

        emit(DataState.Loading)

        try {
            val teamList = pokeBoltInterface.getMyTeam()
            for (item in teamList) {
                val pokemon = pokeBoltInterface.getPokemon("https://pokeapi.co/api/v2/pokemon/${item.id}")
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

            emit(DataState.Success(true))
        }catch (e : Exception) {
            Timber.d( "getTeam: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            emit(DataState.Error(e))

        }
    }

    suspend fun getTeamFromDB(): Flow<List<MyTeamCacheEntity>> {
        return myTeamDao.getTeamList()
    }

    suspend fun getCapturedFromDB(): Observable<List<CapturedCacheEntity>> {
        return capturedDao.getCapturedList()
    }

    suspend fun capturePokemon(capturedPokemonItem: PostPokemonItem) = liveData {

        /**
         * This function is responsible for sending a POST request to the /capture endpoint. This is used to store a new pokemon to the cloud.
         * We then update our local database with the same.
         * */

        emit(DataState.Loading)

        try {
            Timber.d("Entity : ${capturedPokemonItem.toEntity()}")
            val postPokemonResponse = pokeBoltInterface.postPokemon(capturedPokemonItem.toEntity())

            //Insert to Team table
            if (myTeamDao.exists(capturedPokemonItem.id.toInt())) {
                Timber.d("Insert status : Pokemon exists with ID : ${capturedPokemonItem.id}")
            } else {
                val insertStatus = myTeamDao.insert(capturedPokemonItem.toTeamCacheEntity())
                Timber.d("Insert status : $insertStatus")
            }

            //Insert to Captured table
            if (capturedDao.exists(capturedPokemonItem.id.toInt())) {
                Timber.d("Insert status : Pokemon exists with ID : ${capturedPokemonItem.id}")
            } else {
                val insertStatus = capturedDao.insert(capturedPokemonItem.toCapturedCacheEntity())
                Timber.d("Insert status : $insertStatus")
            }

            emit(DataState.Success(postPokemonResponse))
        }catch(e : Exception) {
            Timber.d( "capturePokemon: ERROR : ${e.message} : Cause : ${e.cause} : StackTrace : ${e.printStackTrace()}")
            emit(DataState.Error(e))

        }
    }
}