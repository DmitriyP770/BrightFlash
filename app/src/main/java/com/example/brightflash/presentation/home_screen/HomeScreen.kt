package com.example.brightflash.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brightflash.domain.word.model.Word
import com.example.brightflash.presentation.comon.SearchBar
import com.example.brightflash.presentation.home_screen.HomeViewModel
import com.example.brightflash.presentation.home_screen.WordCard
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onAddWordClick: () -> Unit,
    onPLayOneClick: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    var shouldShowWordCard by remember {
        mutableStateOf(false)
    }
    var shouldShowErrorCard by remember {
        mutableStateOf(false)
    }
    var shouldShowWordNotFoundCard by remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest {event ->
            when(event){
                is HomeViewModel.UIEvent.ShowErrorCard -> {
                    shouldShowWordCard = false
                    shouldShowErrorCard = false

                }
                HomeViewModel.UIEvent.ShowProgressBar -> {
                    shouldShowWordCard = false
                    shouldShowErrorCard = false

                }
                HomeViewModel.UIEvent.ShowWordCard -> {
                    shouldShowWordCard = true
                    shouldShowErrorCard = false

                }
                HomeViewModel.UIEvent.CloseWordInfoCard ->{
                    shouldShowWordCard = false
                    shouldShowErrorCard = false
                }
                HomeViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "Word has been saved in your list"
                    )
                }
                HomeViewModel.UIEvent.ShowWordWasNotFoundCard ->{
                    shouldShowWordNotFoundCard  =true
                    shouldShowErrorCard = false

                }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddWordClick
            , shape = CircleShape) {
                Icon(
                    imageVector = Icons.Default.Add ,
                    contentDescription = "add word"
                )
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 6.dp)
        ) {
            SearchBar(
                searchQuery = viewModel.searchQuery.value ,
                keyboardOptions = KeyboardOptions() ,
                keyboardActions = KeyboardActions() ,
                onQueryChanged = viewModel::searchForDefinition,
                onClearQuery = {viewModel.clearSearchQuery()}
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (shouldShowWordCard){
                WordCard(
                    meanings = viewModel.state.value.wordInfo!!.meanings ,
                    word = viewModel.searchQuery.value ,
                    onClose = { viewModel.closeWordInfoCard() } ,
                    onSave = {
                        viewModel.saveWord(
                            Word(
                                word = viewModel.searchQuery.value ,
                                translation = viewModel.translationText.value
                            )
                        )
                    } ,
                    onTranslationValueChanged = viewModel::setTranslationValue,

                    translationValue = viewModel.translationText.value ,
                )
            }
            if (viewModel.state.value.isLoading){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
            if (shouldShowErrorCard){
                ErrorCard(msg = viewModel.state.value.errorText ?: "Some error occurred")
            }
            if (shouldShowWordNotFoundCard){
                ErrorCard(msg = "Word has not been found")
            }
            Button(onClick =  onPLayOneClick ) {
                Text(text = "PLAY 1")
            }
        }
    }

}

@Composable
fun ErrorCard(modifier : Modifier = Modifier, msg: String) {
    Card(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
            contentAlignment = Alignment.Center

            ) {
            Text(text = msg)
        }
    }
}

