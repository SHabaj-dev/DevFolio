package com.sbz.devfolio.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Rounded.Home)
    object Projects : Screen("projects", "Projects", Icons.Rounded.Folder)
    object Experience : Screen("experience", "Experience", Icons.Rounded.Timeline)
    object Profile : Screen("profile", "Profile", Icons.Rounded.Person)
    object More : Screen("more", "More", Icons.Rounded.MoreHoriz)
}

val BottomNavScreens = listOf(
    Screen.Home,
    Screen.Projects,
    Screen.Experience,
    Screen.Profile,
    Screen.More
)
