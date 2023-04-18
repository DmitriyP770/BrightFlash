package com.example.brightflash.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightflash.domain.game.repository.GameRepository
import com.example.brightflash.domain.word.WordRepository
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.util.Constants
import com.example.brightflash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wordRepository : WordRepository,
    private val gameRepository : GameRepository
) : ViewModel() {
    private val _searchQuery = mutableStateOf<String>("")
    val searchQuery: State<String>
        get() = _searchQuery
    private val _translationText = mutableStateOf("")
    val translationText: State<String>
        get() = _translationText
    private val _state = mutableStateOf(HomeScreenState(
        errorText = "",
        isLoading = false
    ))
    val state: State<HomeScreenState>
        get() = _state
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent>
        get() = _eventFlow

    private var _searchJob: Job? = null

    fun searchForDefinition(searchQuery: String) {
        _searchQuery.value = searchQuery
        _searchJob?.cancel()
        if (_searchQuery.value.isNotBlank()) {
            _searchJob = viewModelScope.launch {
                delay(Constants.SEARCH_DELAY)
                println("sending request $searchQuery")
                wordRepository.getWordDefinition(searchQuery).onEach {result ->
                    when(result){
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                errorText = result.msg,
                                isError = true
                            )
                            _eventFlow.emit(
                                UIEvent.ShowErrorCard(
                                    msg = _state.value.errorText ?: " An error occurred"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                            _eventFlow.emit(UIEvent.ShowProgressBar)
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                wordInfo = result.data!!,
                                isError = false,
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowWordCard)
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    fun closeWordInfoCard() {
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.CloseWordInfoCard)

        }
    }
    fun setTranslationValue(value: String){
        _translationText.value = value
    }

    fun clearSearchQuery(){
        _searchQuery.value =""
    }

    fun saveWord(word : Word){
        viewModelScope.launch {
            wordRepository.saveWordIntoDb(word)
            _eventFlow.emit(UIEvent.CloseWordInfoCard)
            _eventFlow.emit(UIEvent.ShowSnackBar)
        }
    }

    sealed interface UIEvent{
        //When response is successful
        object ShowWordCard : UIEvent
        //When Loading
        object ShowProgressBar : UIEvent
        // When Error
        data class ShowErrorCard(val msg: String) : UIEvent
        //close word card
        object CloseWordInfoCard : UIEvent
        //show SnackBar when word is saved into db
        object ShowSnackBar : UIEvent
    }



}