package com.example.brightflash.presentation.game_card_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brightflash.domain.word.model.Word
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameCardScreen(
    modifier : Modifier = Modifier,
    viewModel : GameCardViewModel
) {

    val state by viewModel.gameState.collectAsState(GameCardState())
    if (!state.isGameFinished){
        GameScreen(
            word = state.currentWord ,
            onKnowButtonClick = {
                viewModel.handleUserInteraction(isKnown = true , word = state.currentWord)
            } ,
            onDunnoButtonClick = {
                viewModel.handleUserInteraction(isKnown = false , word = state.currentWord)

            },
            modifier = Modifier.fillMaxSize()
        )
    }
    if (state.isLoading){
        WordGameCardLoadingScreen()
    }
    
    if (state.isGameFinished){
        GameFinishScreen(knownWords = state.knownWords, unknownWords = state.unknownWords)
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
private fun WordGameCardLoadingScreen(
    modifier : Modifier = Modifier.fillMaxSize()
) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun GameFinishScreen(
    modifier : Modifier = Modifier.fillMaxSize(),
    knownWords: List<Word>,
    unknownWords: List<Word>
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Overview", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Known Words: ", color = Color.Green)
        Spacer(modifier = Modifier.height(8.dp))
        knownWords.forEach {
            Text(text = it.word)
        }
        Divider()
        Text(text = "Unknown words:", color = Color.Red)
        unknownWords.forEach {
            Text(text = it.word)
        }
    }

}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    word : Word,
    onKnowButtonClick: () -> Unit,
    onDunnoButtonClick: () ->  Unit
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
                    onKnowButtonClick()
                } ,
                modifier = Modifier.background(color = Color.Green)
            ) {
                Text(text = "Know")
            }
            Button(
                onClick = {
                    isGuessed = !isGuessed
                    onDunnoButtonClick()
                } ,
                modifier = Modifier.background(color = Color.Red)
            ) {
                Text(text = "Don't know")
            }
        }
    }
}