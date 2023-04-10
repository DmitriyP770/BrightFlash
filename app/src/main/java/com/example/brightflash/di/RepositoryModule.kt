package com.example.brightflash.di

import com.example.brightflash.data.words.GameRepositoryImpl
import com.example.brightflash.data.words.WordRepositoryImpl
import com.example.brightflash.domain.game.repository.GameRepository
import com.example.brightflash.domain.word.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideGameRepository(gameRepositoryImpl : GameRepositoryImpl): GameRepository

    @Binds
    @Singleton
    abstract fun provideWordRepository(wordRepositoryImpl : WordRepositoryImpl): WordRepository

}