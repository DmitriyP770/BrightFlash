package com.example.brightflash.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.brightflash.util.IconResource

@Composable
fun MainScreen(
    navHostController : NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { BottomNavBar(navHostController = navHostController) } ,
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {navHostController.navigate(BottomBarScreens.AddWordScreen.route)},

            ) {
                Icon(
                    painter = BottomBarScreens.SavedWords.icon.asPainterResource() ,
                    contentDescription = "Add word icon"
                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            RootNavGraph(navHostController = navHostController)
        }
    }

}

@Composable
fun BottomNavBar(
    navHostController : NavHostController,

) {
    val screens = listOf<BottomBarScreens>(
        BottomBarScreens.Home,
        BottomBarScreens.SavedWords
    )
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val bottombarDestionation = screens.any { it.route == currentDestination?.route }
    if (bottombarDestionation){
        BottomNavigation() {
            screens.forEach {screen ->
                BottomNavigationItem(
                    label = { Text(text = screen.title)},
                    icon = { Icon(painter = screen.icon.asPainterResource() , contentDescription = "icon of screen", tint = Color.White) },
                    selected =  currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true,
                    onClick = {
                        navHostController.navigate(screen.route){
                            popUpTo(navHostController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }



}