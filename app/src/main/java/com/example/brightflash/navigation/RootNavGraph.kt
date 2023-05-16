package com.example.brightflash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavGraph(
    navHostController : NavHostController,
    savedWordsScreen: @Composable () -> Unit,
    homeScreen: @Composable () -> Unit ,
    addWordScreen: @Composable () -> Unit,
    gameCardScreen: @Composable () -> Unit

) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT ,
        startDestination = AppScreens.Home.route
    ){
        homeNavGraph(
            homeScreen, addWordScreen
        )

        composable(route = AppScreens.SavedWords.route){
            savedWordsScreen()
        }
        composable(route = AppScreens.GameCardGameScreen.route){
            gameCardScreen()
        }

    }
}

object Graph{
    const val ROOT = "ROOT"
    const val HOME = "HOME"
}