package com.example.brightflash.presentation.comon

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(
    searchQuery: String,
    keyboardOptions : KeyboardOptions,
    keyboardActions : KeyboardActions,
    onQueryChanged: (String) -> Unit,
    modifier : Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery ,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = "Search")},
        leadingIcon = { Icon(
            imageVector = Icons.Default.Search ,
            contentDescription = "Search Icon"
        )},
        trailingIcon = { Icon(
            imageVector = Icons.Default.Close ,
            contentDescription = "Delete search query"
        )},
        modifier = modifier.fillMaxWidth(),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}