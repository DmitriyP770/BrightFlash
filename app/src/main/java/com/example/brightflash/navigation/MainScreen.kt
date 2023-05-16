package com.example.brightflash.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.brightflash.presentation.HomeScreen
import com.example.brightflash.presentation.add_word_screen.AddWordScreen
import com.example.brightflash.presentation.game_card_screen.GameCardScreen
import com.example.brightflash.presentation.saved_words_screen.SavedWordsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    scaffoldState : ScaffoldState = rememberScaffoldState() ,
) {
    val navState = rememberNavState()
    Scaffold(
        bottomBar = { BottomNavBar(navState) } ,
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        scaffoldState = scaffoldState

    ) {
        Box(modifier = Modifier.padding(it)) {
            RootNavGraph(
                navHostController = navState.navHostController,
                homeScreen = {
                    HomeScreen(
                        onAddWordClick = { navState.navigateTo(AppScreens.AddWordScreen.route) } ,
                        onPLayOneClick = { navState.navigateTo(AppScreens.GameCardGameScreen.route) }
                    )
                } ,
                savedWordsScreen = { SavedWordsScreen(savedWordsViewModel = hiltViewModel()) } ,
                addWordScreen = {
                    AddWordScreen(
                        viewModel = hiltViewModel() ,
                        onBackClick = { navState.navigateTo(AppScreens.StartScreen.route) } ,
                        scaffoldState = scaffoldState
                    )
                } ,
                gameCardScreen = { GameCardScreen(viewModel = hiltViewModel()) }

            )
        }
    }

}

@SuppressLint("SuspiciousIndentation")
@Composable
fun BottomNavBar(
    navState : NavState
) {
    val screens = listOf<AppScreens>(
        AppScreens.Home ,
        AppScreens.SavedWords
    )
    val backStackEntry by navState.navHostController.currentBackStackEntryAsState()
    val currentRout = backStackEntry?.destination?.route
        BottomNavigation() {
            screens.forEach { screen ->
                BottomNavigationItem(

                    label = { Text(text = screen.title) } ,
                    icon = {
                        Icon(
                            painter = screen.icon!!.asPainterResource() ,
                            contentDescription = "icon of screen" ,
                            tint = Color.White
                        )
                    } ,
                    selected = currentRout == screen.route ,
                    onClick = {
                        navState.navigateTo(screen.route)
                    }
                )
            }
        }

}