package com.example.brightflash.di

import android.app.Application
import androidx.room.Room
import com.example.brightflash.data.local.BrightFlashDatabase
import com.example.brightflash.data.local.converters.Converters
import com.example.brightflash.data.remote.DictionaryApi
import com.example.brightflash.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(application : Application): BrightFlashDatabase = Room.databaseBuilder(
        application ,
        BrightFlashDatabase::class.java ,
        "brightflash_db"
    ).addTypeConverter(Converters()).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideApi() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(DictionaryApi::class.java)
}