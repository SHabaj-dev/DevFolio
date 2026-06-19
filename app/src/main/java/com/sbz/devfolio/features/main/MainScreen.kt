package com.sbz.devfolio.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sbz.devfolio.core.designsystem.components.ClayBottomNavigation
import com.sbz.devfolio.core.navigation.BottomNavScreens
import com.sbz.devfolio.core.navigation.Screen
import com.sbz.devfolio.features.home.HomeRoute
import com.sbz.devfolio.features.projects.ProjectsScreen
import com.sbz.devfolio.features.experience.ExperienceScreen
import com.sbz.devfolio.features.profile.ProfileScreen
import com.sbz.devfolio.features.more.MoreScreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Use a Box so the content can scroll behind the floating Clay Navigation
    Box(modifier = modifier.fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Home.route) { HomeRoute() }
            composable(Screen.Projects.route) { ProjectsScreen() }
            composable(Screen.Experience.route) { ExperienceScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.More.route) { MoreScreen() }
        }

        // Only show bottom navigation for main routes
        val isBottomNavRoute = BottomNavScreens.any { it.route == currentRoute }
        if (isBottomNavRoute || currentRoute == null) {
            ClayBottomNavigation(
                screens = BottomNavScreens,
                currentRoute = currentRoute ?: Screen.Home.route,
                onNavigateTo = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

