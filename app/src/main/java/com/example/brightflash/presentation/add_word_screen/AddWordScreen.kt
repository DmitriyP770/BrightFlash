package com.example.brightflash.presentation.add_word_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddWordScreen(
    word: String
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 6.dp)
    ) {
       TextField(value = word , onValueChange = {})
       Spacer(modifier = Modifier.height(4.dp))
       TextField(value = "" , onValueChange = {}, )
    }
}