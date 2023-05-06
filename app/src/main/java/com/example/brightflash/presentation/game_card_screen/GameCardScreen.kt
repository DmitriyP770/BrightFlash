package com.example.brightflash.presentation.game_card_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.brightflash.domain.word.model.Word
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameCardScreen(
    modifier : Modifier = Modifier,
    word : Word,
    viewModel : GameCardViewModel
) {
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                GameCardViewModel.WordGameUIEvent.ShowListIsEmptyCard -> {}
                GameCardViewModel.WordGameUIEvent.ShowProgressBar -> {}
            }
        }
    }
    val state = viewModel.gameState.value
    if (!state.isGameFinished){
        GameScreen(
            word = state.currentWord ,
            onKnowButtonClick = {
                viewModel.handleUserInteraction(isKnown = true , word = word)
            } ,
            onDunnoButtonClick = {
                viewModel.handleUserInteraction(isKnown = false , word = word)

            }
        )
    }

}

@Composable
private fun WordGameCard(
    word : Word,
    isGuessed: Boolean
) {

    Card(
        elevation = 1.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier.size(200.dp),

    ) {

        Box(
            modifier = Modifier.fillMaxSize() ,
            contentAlignment = Alignment.Center
        ) {
            Text(text = if (isGuessed){
                word.translation
            } else{
                word.word
            }
            )
        }
    }
}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    word : Word,
    onKnowButtonClick: () -> Unit,
    onDunnoButtonClick: () -> Unit
) {
    var isGuessed by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        WordGameCard(word = word , isGuessed = isGuessed)
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    isGuessed = !isGuessed
                    onKnowButtonClick
                },
                modifier = Modifier.background(color = Color.Green)
            ) {
                Text(text = "Know")
            }
            Button(
                onClick = {
                    isGuessed = !isGuessed
                    onDunnoButtonClick
                } ,
                modifier = Modifier.background(color = Color.Red)
            ) {
                Text(text = "Don't know")
            }
        }
    }
}