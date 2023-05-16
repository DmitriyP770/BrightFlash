package com.example.brightflash.presentation.game_card_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightflash.domain.game.repository.GameRepository
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class GameCardViewModel @Inject constructor(
    private val repository : GameRepository
): ViewModel(){

    private val _gameState = MutableStateFlow(GameCardState())
    val gameState: SharedFlow<GameCardState> = _gameState.asSharedFlow()
    private val words = mutableListOf<Word>()
    private val _eventFlow = MutableSharedFlow<WordGameUIEvent>()
    val eventFlow: SharedFlow<WordGameUIEvent> = _eventFlow
    private var currentWordIndex = 0
    private val _wordsStack: ArrayDeque<Word> = ArrayDeque()
    private val isFirstLaunch: Boolean = false
    private val _knownWords = mutableListOf<Word>()
    private val _unknownWords = mutableListOf<Word>()

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
                        fillStack(words)
                        showNextCard()

                    }
                }
            }.launchIn(this)
        }
    }

    private fun showNextCard(){
        viewModelScope.launch {
            if (_wordsStack.isNotEmpty()){
                _eventFlow.emit(WordGameUIEvent.ShowWordCard)
                val word = _wordsStack.removeLast()
                _gameState.emit(GameCardState(currentWord = word))
            } else if (isFirstLaunch){
                _eventFlow.emit(WordGameUIEvent.ShowListIsEmptyCard)
            } else{
                _eventFlow.emit(WordGameUIEvent.ShowResultScreen)
            }
        }
    }
//loading words from db
    //Show first Card
    //Handle interaction -> change words
    //if stack is empty -> show result screen
    private fun fillStack(words: List<Word>){
        //fill the stack
        words.forEach {
            _wordsStack.add(it)
        }
    }

//    private fun conductGame(){
//        viewModelScope.launch {
//            _eventFlow.emit(WordGameUIEvent.ShowProgressBar)
//            getListOfWords()
//            if (_wordsStack.isEmpty()) {
//                _eventFlow.emit(WordGameUIEvent.ShowListIsEmptyCard)
//                return@launch
//            } else{
//                while (_wordsStack.isNotEmpty()) {
//                    val word = _wordsStack.removeLast()
//                    _gameState.emit(
//                        GameCardState(
//                            currentWord = word
//                        )
//                    )
//                    _eventFlow.emit(WordGameUIEvent.ShowWordCard)
//                }
//                _eventFlow.emit(WordGameUIEvent.ShowResultScreen)
//                return@launch
//            }
//
//        }
//
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleUserInteraction(isKnown: Boolean , word : Word) = viewModelScope.launch {
        if (isKnown){
            val today = OffsetDateTime.now()
            var repeats = word.qtyOfRepeats
            val newWord = word.copy(qtyOfRepeats = ++repeats, lastRepeate = today)
            repository.updateWordRepeats(newWord)
            _knownWords.add(word)
            if (_wordsStack.isEmpty()){
                _gameState.emit(GameCardState(
                    isGameFinished = true ,
                    knownWords = _knownWords ,
                    unknownWords = _unknownWords
                ))
            } else{
                showNextCard()
            }
        } else{
            _unknownWords.add(word)
            if (_wordsStack.isEmpty()){
                _gameState.emit(GameCardState(
                    isGameFinished = true ,
                    knownWords = _knownWords ,
                    unknownWords = _unknownWords
                ))
            } else{
                showNextCard()
            }
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
        viewModelScope.launch {
            getListOfWords()
        }

    }


    sealed interface WordGameUIEvent{
        object ShowListIsEmptyCard : WordGameUIEvent
        object ShowProgressBar : WordGameUIEvent
        object ShowWordCard: WordGameUIEvent
        object ShowResultScreen : WordGameUIEvent

    }
}