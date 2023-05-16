package com.example.brightflash.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.brightflash.data.local.BrightFlashDatabase
import com.example.brightflash.data.remote.DictionaryApi
import com.example.brightflash.domain.word.WordRepository
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.domain.word.model.WordInfo
import com.example.brightflash.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val db: BrightFlashDatabase,
    private val api: DictionaryApi
): WordRepository {
    private val dao = db.dao
    override suspend fun getWordDefinition(word : String) : Flow<Resource<WordInfo>> = flow {
        println("sending request $word 1")
        emit(Resource.Loading())
        try {
            println("sending request $word 2")
            val response = api.getWordsInfo(word)
            println("callcallcall ${response.first().word}")

            emit(Resource.Success(data = response.first().toWordInfo()))
        } catch (e: Exception){
            println("sending request $word 3")
            println("callcallcall ${e.localizedMessage}")
            emit(Resource.Error(msg = e.localizedMessage ?: "An error occurred when tried to obtain definition"))
        }
    }

    override suspend fun getAllWords() : Flow<List<Word>>  = flow {
        dao.getAllWords().collect {dbWords ->
            emit(dbWords.map {it.toWord()})
        }
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun saveWordIntoDb(word : Word) {
        dao.saveWord(word.toWordEntity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteWord(word : Word) {
        dao.deleteWord(word.toWordEntity())
    }

    override suspend fun deleteWordBySpell(spell : String) {
        dao.deleteByWordSpell(spell)
    }
}