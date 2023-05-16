package com.example.brightflash.presentation.add_word_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightflash.domain.word.WordRepository
import com.example.brightflash.domain.word.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWordViewModel @Inject constructor(
    private val repository : WordRepository
) : ViewModel() {
    private val _translationValue = mutableStateOf("")
    val translationValue: State<String>
        get() = _translationValue
    private val _wordValue = mutableStateOf("")
    val wordValue: State<String>
        get() = _wordValue
    private val _exampleValue = mutableStateOf("")
    val exampleValue: State<String>
        get() = _exampleValue
    private val _eventFlow = MutableSharedFlow<AddWordUIEvent>()
    val eventFlow: SharedFlow<AddWordUIEvent> = _eventFlow.asSharedFlow()

    fun saveWordIntoDb(){
        viewModelScope.launch {
            repository.saveWordIntoDb(word = Word(
                word = _wordValue.value ,
                translation = _translationValue.value
            ))
            _eventFlow.emit(AddWordUIEvent.ShowSnackBar)
            println("EVENTFLOWFROM ${_eventFlow}")
        }
    }

    fun dismissSavingWord(){
        viewModelScope.launch {
            repository.deleteWordBySpell(_wordValue.value)
            _eventFlow.emit(AddWordUIEvent.Empty)
        }
    }

    fun setWordValue(word: String){
        _wordValue.value = word
    }

    fun setTranslationValue(translation: String){
        _translationValue.value = translation
    }

    fun setExampleValue(example: String){
        _exampleValue.value = example
    }

    sealed interface AddWordUIEvent{
        object ShowSnackBar : AddWordUIEvent
        object Empty: AddWordUIEvent
    }

}