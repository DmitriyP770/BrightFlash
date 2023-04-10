package com.example.brightflash.data.words

import com.example.brightflash.data.local.BrightFlashDatabase
import com.example.brightflash.data.remote.DictionaryApi
import com.example.brightflash.domain.word.WordRepository
import com.example.brightflash.domain.word.model.Meaning
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.util.Resource
import com.example.brightflash.util.toMeaning
import com.example.brightflash.util.toWord
import com.example.brightflash.util.toWordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val db: BrightFlashDatabase,
    private val api: DictionaryApi
): WordRepository {
    private val dao = db.dao
    override suspend fun getWordDefinition(word : String) : Flow<Resource<List<Meaning>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getWordsInfo(word)
            emit(Resource.Success(data = response.first().meanings.map { it.toMeaning() }))
        } catch (e: Exception){
            emit(Resource.Error(msg = e.localizedMessage ?: "An error occurred when tried to obtain definition"))
        }
    }

    override suspend fun getAllWords() : List<Word> {
       return dao.getAllWords().map { it.toWord() }
    }

    override suspend fun saveWordIntoDb(word : Word) {
        dao.saveWord(word.toWordEntity())
    }

    override suspend fun deleteWord(word : Word) {
        dao.deleteWord(word.word)
    }
}