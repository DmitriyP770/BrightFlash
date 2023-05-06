package com.example.brightflash.presentation.saved_words_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brightflash.domain.word.model.Word

@OptIn(ExperimentalMaterialApi::class , ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SavedWordsScreen(
    savedWordsViewModel: SavedWordsViewModel

) {
    val state = savedWordsViewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center , modifier = Modifier.fillMaxWidth()) {
                Text(text = "My Words" , fontWeight = FontWeight.Bold ,)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween ,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Word")
                Text(text = "Translation")
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 6.dp) ,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = state.value ,
                    key = { word -> word.hashCode() }
                ) {
                    val dismissState = rememberDismissState()
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        savedWordsViewModel.deleteWordFromDb(it)
                    }
                    SwipeToDismiss(
                        state = dismissState ,
                        directions = setOf(DismissDirection.EndToStart) ,
                        background = {} ,
                        modifier = Modifier
                            .animateItemPlacement()
                    ) {
                        SavedWordCard(word = it)
                    }
                }
            }


        }
    }}
    @Composable
    fun SavedWordCard(
        word : Word ,
        modifier : Modifier = Modifier
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween ,
                modifier = modifier.height(30.dp)
            ) {
                Text(text = word.word , fontSize = 24.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = word.translation , fontSize = 24.sp)
            }
        }

    }

    @OptIn(ExperimentalMaterialApi::class , ExperimentalFoundationApi::class)
    @Composable
    fun SavedWordsColumn(
        words : List<Word> ,
        viewModel : SavedWordsViewModel ,
        modifier : Modifier = Modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 6.dp) ,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = words ,
                key = { word -> word.hashCode() }
            ) {
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deleteWordFromDb(it)
                }
                SwipeToDismiss(
                    state = dismissState ,
                    directions = setOf(DismissDirection.EndToStart) ,
                    background = {} ,
                    modifier = Modifier
                        .animateItemPlacement()
                ) {
                    SavedWordCard(word = it)
                }
            }

            //                {
//                    SwipeToDismiss(state =  , background =, dismissContent =  )
//
//                    SavedWordCard(word = it, modifier = Modifier.fillMaxWidth())
//                    Divider()
//                }
        }
    }



//@Composable
//@OptIn(ExperimentalMaterialApi::class)
//fun SwipeBackground(dismissState: DismissState) {
//    val direction = dismissState.dismissDirection ?: return
//
//    val color by animateColorAsState(
//        when (dismissState.targetValue) {
//            DismissValue.Default -> Color.White
//            DismissValue.DismissedToEnd -> Color.Green
//        }
//    )
//    val alignment = when (direction) {
//        DismissDirection.StartToEnd -> Alignment.CenterStart
//        DismissDirection.EndToStart -> Alignment.CenterEnd
//    }
//    val icon = when (direction) {
//        DismissDirection.EndToStart -> {
//            Icons.Default.Delete
//        }
//        DismissDirection.StartToEnd -> {}
//    }
//    val scale by animateFloatAsState(
//        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
//    )
//
//    Box(
//        Modifier
//            .fillMaxSize()
//            .background(color)
//            .padding(horizontal = 20.dp),
//        contentAlignment = alignment
//    ) {
//        Icon(
//            icon,
//            contentDescription = "Localized description",
//            modifier = Modifier.scale(scale)
//        )
//    }
//}