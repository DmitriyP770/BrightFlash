package com.example.brightflash.presentation.add_word_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightflash.domain.word.WordRepository
import com.example.brightflash.domain.word.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun saveWordIntoDb(){
        viewModelScope.launch {
            repository.saveWordIntoDb(word = Word(
                word = _wordValue.value ,
                translation = _translationValue.value
            ))
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

}