package com.sivan.pokebolt.di

import android.content.Context
import androidx.room.Room
import com.sivan.pokebolt.database.PokeBoltDatabase
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


}