package com.example.brightflash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brightflash.presentation.comon.SearchBar
import com.example.brightflash.presentation.home_screen.HomeViewModel
import com.example.brightflash.presentation.home_screen.WordCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    var shouldShowWordCard by remember {
        mutableStateOf(false)
    }
    var shouldShowErrorCard by remember {
        mutableStateOf(false)
    }

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
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            searchQuery = viewModel.searchQuery.value ,
            keyboardOptions = KeyboardOptions() ,
            keyboardActions = KeyboardActions() ,
            onQueryChanged = viewModel::searchForDefinition
        )
        Spacer(modifier = Modifier.height(6.dp))
        if (shouldShowWordCard){
            WordCard(
                meanings = viewModel.state.value.wordInfo!!.meanings ,
                word = viewModel.searchQuery.value ,
                onclick = { viewModel.closeWordInfoCard() }
            )
        }
        if (viewModel.state.value.isLoading){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
        if (viewModel.state.value.isError){
            ErrorCard(msg = viewModel.state.value.errorText ?: "Some error occurred")
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
