package com.example.brightflash.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import com.example.brightflash.R
import com.example.brightflash.util.IconResource

sealed class BottomBarScreens(val route: String, val icon: IconResource, val title: String){

    object Home : BottomBarScreens(
        route = "HomeScreen" ,
        icon = IconResource.fromImageVector(Icons.Default.Home) ,
        title = "Home"
    )

    object SavedWords : BottomBarScreens(
        route = "Saved Words" ,
        icon = IconResource.fromDrawableResource(R.drawable.baseline_library_books_24) ,
        title = "My Words"
    )

    object AddWordScreen : BottomBarScreens(
        route = "AddWord" ,
        icon = IconResource.fromImageVector(Icons.Default.Add) ,
        title = "Add"
    )

}
