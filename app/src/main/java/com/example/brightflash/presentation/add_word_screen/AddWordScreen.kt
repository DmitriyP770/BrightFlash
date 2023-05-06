package com.example.brightflash.presentation.add_word_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddWordScreen(
    viewModel : AddWordViewModel
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
    Scaffold(modifier = Modifier.padding(horizontal = 6.dp).fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp)
        ) {
            TextField(
                value = viewModel.wordValue.value ,
                onValueChange = viewModel::setWordValue ,
                label = { Text(text = "Enter a word") },
                keyboardActions = keyboardActionsFirst,
                keyboardOptions = keyboardOptionsFirst
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

}