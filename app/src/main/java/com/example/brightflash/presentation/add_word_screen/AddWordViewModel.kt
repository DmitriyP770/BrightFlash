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

    fun saveWordIntoDb(word : Word){
        viewModelScope.launch {
            repository.saveWordIntoDb(word)
        }
    }
}