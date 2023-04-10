package com.example.brightflash.data.words

import com.example.brightflash.data.local.BrightFlashDatabase
import com.example.brightflash.domain.game.model.WordQuestion
import com.example.brightflash.domain.game.repository.GameRepository
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val db: BrightFlashDatabase
) : GameRepository {

    override suspend fun generateWordTranslateQuestions() : Flow<Resource<List<WordQuestion>>> = flow {
        emit(Resource.Loading())
        try {
            val words = db.dao.getRandomTenWords()
            val result = mutableListOf<WordQuestion>()
            words.forEach {dbWord ->
                result.add(
                    WordQuestion(
                        question = dbWord.word ,
                        answer = dbWord.translation ,
                        //TODO: Think bout how to generate options
                        answerOptions = emptyList() ,
                        isCorrect = false
                ))
                emit(Resource.Success(data = result.toList()))
            }
        } catch (e: Exception){
            emit(Resource.Error(msg = e.localizedMessage ?: "An error occurred when trying to get words"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun generateTranslateWordQuestion(): Flow<Resource<List<WordQuestion>>> = flow {
        emit(Resource.Loading())
        try {
            val words = db.dao.getRandomTenWords()
            val result = mutableListOf<WordQuestion>()
            words.forEach {dbWord ->
                result.add(
                    WordQuestion(
                        question = dbWord.translation,
                        answer = dbWord.word ,
                        //TODO: Think bout how to generate options
                        answerOptions = emptyList() ,
                        isCorrect = false
                    ))
                emit(Resource.Success(data = result.toList()))
            }
        } catch (e: Exception){
            emit(Resource.Error(msg = e.localizedMessage ?: "An error occurred when trying to get words"))
        }
    }.flowOn(Dispatchers.IO)

    override fun finishGame() {
        TODO("Not yet implemented")
    }


}