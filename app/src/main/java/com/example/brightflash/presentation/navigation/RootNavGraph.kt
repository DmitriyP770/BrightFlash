package com.example.brightflash.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.brightflash.presentation.HomeScreen
import com.example.brightflash.presentation.add_word_screen.AddWordScreen
import com.example.brightflash.presentation.saved_words_screen.SavedWordsScreen

@Composable
fun RootNavGraph(
    navHostController : NavHostController,

) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = BottomBarScreens.Home.route
    ){
        composable(route = BottomBarScreens.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreens.SavedWords.route){
            SavedWordsScreen(savedWordsViewModel = hiltViewModel())
        }
        composable(route = BottomBarScreens.AddWordScreen.route){
            AddWordScreen( viewModel = hiltViewModel())
        }

    }
}

object Graph{
    const val ROOT = "ROOT"
    const val HOME = "HOME"
}