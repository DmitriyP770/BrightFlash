package com.example.brightflash.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brightflash.domain.word.model.Meaning

@Composable
fun WordCard(
    meanings: List<Meaning> ,
    word: String ,
    modifier : Modifier = Modifier ,
    onClose : () -> Unit ,
    onSave : () -> Unit ,
    onTranslationValueChanged: (String) -> Unit,
    translationValue : String

    ) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = word ,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 24.sp
                )
            }
            TextField(
                value = translationValue ,
                onValueChange = onTranslationValueChanged ,
                placeholder = { Text(text = "Enter translation") },
                modifier = Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                Text(
                    text = "Definitions",
                    fontSize = 16.sp
                )
            }
            meanings.forEach {
                WordInfoSegment(
                    partOfSpeech = it.partOfSpeech ,
                    definition = it.definitions.first().definition ,
                    example = it.definitions.first().example
                )
            }
            CardButton(label = "Save", color = Color.Green, onclick = onSave)
            Spacer(modifier = Modifier.height(4.dp))
            CardButton(label = "Close", color = Color.Red, onclick = onClose)

        }
    }
}

@Composable
fun WordInfoSegment(
    modifier : Modifier = Modifier,
    partOfSpeech: String,
    definition: String,
    example: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth() , contentAlignment = Alignment.Center) {
            Text(text = "Part of speech: $partOfSpeech" , fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Definition:", fontWeight = FontWeight.Bold)
            Text(text = definition)

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Example:", fontWeight = FontWeight.Bold)
            Text(text = example)

        }
            
        
    }
}

@Composable
fun CardButton(
    modifier : Modifier = Modifier,
    label: String,
    color: Color,
    onclick: () -> Unit
) {
    Button(
        onClick = onclick ,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(text = label)
    }
}