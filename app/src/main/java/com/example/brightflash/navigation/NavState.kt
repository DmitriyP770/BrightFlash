package com.example.brightflash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavState(
    val navHostController : NavHostController
) {
    fun navigateTo(route: String){
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.startDestinationId)
            launchSingleTop = true
        }
    }
}

@Composable
fun rememberNavState(
    navHostController : NavHostController = rememberNavController(),
): NavState {
    return remember {
        NavState(navHostController)
    }
}