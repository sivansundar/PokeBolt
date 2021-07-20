package com.sivan.pokebolt.di

import android.content.Context
import androidx.room.Room
import com.sivan.pokebolt.database.PokeBoltDatabase
import com.sivan.pokebolt.database.dao.CapturedDao
import com.sivan.pokebolt.database.dao.MyTeamDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providePokeBoltDatabase(@ApplicationContext context : Context) : PokeBoltDatabase {
        return Room.databaseBuilder(
            context,
            PokeBoltDatabase::class.java,
            "pokebolt_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMyTeamDao(pokeBoltDatabase: PokeBoltDatabase) : MyTeamDao {
        return pokeBoltDatabase.myTeamDao()
    }

    @Singleton
    @Provides
    fun provideCapturedDao(pokeBoltDatabase: PokeBoltDatabase) : CapturedDao {
        return pokeBoltDatabase.capturedDao()
    }
}