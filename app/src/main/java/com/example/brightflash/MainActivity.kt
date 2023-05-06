package com.example.brightflash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brightflash.presentation.HomeScreen
import com.example.brightflash.presentation.home_screen.HomeViewModel
import com.example.brightflash.presentation.navigation.MainScreen
import com.example.brightflash.presentation.ui.theme.BrightFlashTheme
import dagger.hilt.android.AndroidEntryPoint

//BH
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrightFlashTheme {
                MainScreen()
            }
        }
    }
}
