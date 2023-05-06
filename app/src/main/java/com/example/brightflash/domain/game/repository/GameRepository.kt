package com.example.brightflash.domain.game.repository

import com.example.brightflash.domain.game.model.WordQuestion
import com.example.brightflash.domain.game.word_flashcards.WordFlashCard
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.util.Resource
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun generateWordTranslateQuestions(): Flow<Resource<List<WordQuestion>>>

    suspend fun generateTranslateWordQuestion(): Flow<Resource<List<WordQuestion>>>

    fun showWordCard(): WordFlashCard

    fun getWordsForWordCard(): Flow<Resource<List<Word>>>

    fun finishGame()

    suspend fun updateWordRepeats(word : Word)

}