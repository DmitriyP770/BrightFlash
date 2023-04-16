package com.example.brightflash.domain.word

import com.example.brightflash.domain.word.model.Definition
import com.example.brightflash.domain.word.model.Meaning
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.domain.word.model.WordInfo
import com.example.brightflash.util.Resource
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun getWordDefinition(word: String): Flow<Resource<WordInfo>>

    suspend fun getAllWords(): List<Word>

    suspend fun saveWordIntoDb(word : Word)

    suspend fun deleteWord(word : Word)

}