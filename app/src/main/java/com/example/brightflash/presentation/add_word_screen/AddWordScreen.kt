package com.example.brightflash.presentation.add_word_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddWordScreen(
    viewModel : AddWordViewModel,
    onBackClick: () -> Unit,
    scaffoldState : ScaffoldState
) {
    val focusManager = LocalFocusManager.current

    val keyboardActionsFirst = KeyboardActions(onDone = {
        focusManager.moveFocus(focusDirection = FocusDirection.Next)
    })
    val keyboardActionsSecond = KeyboardActions(onDone = {
        focusManager.moveFocus(focusDirection = FocusDirection.Next)
    })
    val keyboardOptionsFirst = KeyboardOptions(imeAction = ImeAction.Next)
    val keyboardOptionsSecond = KeyboardOptions(imeAction = ImeAction.Next)
    val keyboardOptionsFinal = KeyboardOptions(imeAction = ImeAction.Done)
    val keyboardActionsFinal = KeyboardActions(onDone = {
        focusManager.clearFocus()
    })
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
        .fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "Add Word") },
            navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to home screen"
                )

            }
        },)
    },
        scaffoldState = scaffoldState

        ) {
        LaunchedEffect(key1 = true  ){
            viewModel.eventFlow.collectLatest {
                if (it == AddWordViewModel.AddWordUIEvent.ShowSnackBar){
                    val snackBarResult =  scaffoldState.snackbarHostState.showSnackbar(
                        message = "Word has been saved",
                        duration = SnackbarDuration.Long,
                        actionLabel = "Undo"
                    )
                    when(snackBarResult){
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> {viewModel.dismissSavingWord()}
                    }

                }
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 8.dp)
        ) {
            TextField(
                value = viewModel.wordValue.value ,
                onValueChange = viewModel::setWordValue ,
                label = { Text(text = "Enter a word") },
                keyboardActions = keyboardActionsFirst,
                keyboardOptions = keyboardOptionsFirst,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = viewModel.translationValue.value ,
                onValueChange = viewModel::setTranslationValue ,
                label = { Text(text = "Enter a translation") },
                keyboardOptions = keyboardOptionsSecond,
                keyboardActions = keyboardActionsSecond,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = "" ,
                onValueChange = {},
                label = { Text(text = "Add an example")},
                keyboardOptions = keyboardOptionsFinal,
                keyboardActions = keyboardActionsFinal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.saveWordIntoDb()
                    navController.popBackStack()
                } ,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "SAVE")
            }


        }
    }
    BackHandler(onBack = onBackClick)

}