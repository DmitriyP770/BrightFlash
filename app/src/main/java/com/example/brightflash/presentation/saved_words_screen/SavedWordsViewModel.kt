package com.example.brightflash.presentation.saved_words_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightflash.domain.word.WordRepository
import com.example.brightflash.domain.word.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedWordsViewModel @Inject constructor(private val repository : WordRepository) : ViewModel() {

    private val _savedWordsList = mutableListOf<Word>()
    private val _state = MutableStateFlow<List<Word>>((emptyList()))
    val state: StateFlow <List<Word>>
        get() = _state

    private fun getWordsFromDb() = viewModelScope.launch {
        repository.getAllWords().onEach {
            _state.value = it

        }.collect()
    }

    fun deleteWordFromDb(word : Word){
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }

    init {
        getWordsFromDb()
    }

}