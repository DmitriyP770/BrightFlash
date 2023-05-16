package com.example.brightflash.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import com.example.brightflash.R
import com.example.brightflash.util.IconResource

sealed class AppScreens(val route: String , val icon: IconResource? = null , val title: String){

    object Home : AppScreens(
        route =  HOME_SCREEN,
        icon = IconResource.fromImageVector(Icons.Default.Home) ,
        title = "Home"
    )

    object SavedWords : AppScreens(
        route =  SAVED_WORDS,
        icon = IconResource.fromDrawableResource(R.drawable.baseline_library_books_24) ,
        title = "My Words"
    )

    object AddWordScreen : AppScreens(
        route = ADD_WORD ,
        icon = IconResource.fromImageVector(Icons.Default.Add) ,
        title = "Add"
    )
    object StartScreen : AppScreens(
        route = START_SCREEN,
        icon = IconResource.fromImageVector(Icons.Default.Home),
        title = "Start"
        )

    object GameCardGameScreen: AppScreens(
        route = GAME_CARD_SCREEN,
        icon = null,
        title = ""
    )

    private companion object{
        const val HOME_SCREEN = "HomeScreen"
        const val START_SCREEN = "StartScreen"
        const val SAVED_WORDS = "Saved Words"
        const val ADD_WORD = "AddWord"
        const val GAME_CARD_SCREEN = "GameCard"
    }

}
