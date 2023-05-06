package com.example.brightflash.presentation.game_card_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightflash.domain.game.repository.GameRepository
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class GameCardViewModel @Inject constructor(
    private val repository : GameRepository
): ViewModel(){

    private val _gameState = mutableStateOf(GameCardState())
    val gameState: State<GameCardState> = _gameState
    private val words = mutableListOf<Word>()
    private val _eventFlow = MutableSharedFlow<WordGameUIEvent>()
    val eventFlow: SharedFlow<WordGameUIEvent> = _eventFlow
    private var currentWordIndex = 0

    private fun getListOfWords(){
        viewModelScope.launch {
            repository.getWordsForWordCard().onEach {result ->
                when(result){
                    is Resource.Error -> {
                        _eventFlow.emit(WordGameUIEvent.ShowListIsEmptyCard)
                    }
                    is Resource.Loading -> {
                        _eventFlow.emit(WordGameUIEvent.ShowProgressBar)
                    }
                    is Resource.Success -> {
                        words.addAll(result.data!!)
                    }
                }
            }.launchIn(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleUserInteraction(isKnown: Boolean , word : Word) = viewModelScope.launch {
        if (isKnown){
            val today = OffsetDateTime.now()
            var repeats = word.qtyOfRepeats
            val newWord = word.copy(qtyOfRepeats = ++repeats, )
            repository.updateWordRepeats(newWord)
            showNextWord()
        } else{
            showNextWord()
        }

    }

    private fun showNextWord(){
        val wordListSize = words.count()
        if (currentWordIndex == wordListSize-1){
            showFinishGameFragment()
        }
        _gameState.value = _gameState.value.copy(currentWord = words[currentWordIndex])
        currentWordIndex++

    }
    // Finish game fragment it's a screen that show result of a game
    private fun showFinishGameFragment() {
        _gameState.value = _gameState.value.copy(
            currentWord = Word(word = "" , translation = "") ,
            isGameFinished = true
        )

    }

    init {
        getListOfWords()
        showNextWord()
    }


    sealed interface WordGameUIEvent{

        object ShowListIsEmptyCard : WordGameUIEvent
        object ShowProgressBar : WordGameUIEvent

    }
}