package com.sbz.devfolio.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _uiState.update { currentState ->
            currentState.copy(
                name = "Shabaj Ansari",
                title = "Android Software Engineer",
                description = "Android Software Engineer with 3 years of experience building scalable consumer applications, reward-based platforms, and Android SDKs using Kotlin and modern Android architecture.",
                projectsCompleted = 10,
                yearsExperience = 3,
                technologiesUsed = 20,
                githubRepositories = 24,
                techStack = listOf(
                    "Kotlin", "Java", "Jetpack Compose", "MVVM", 
                    "Clean Architecture", "Coroutines", "Flow", 
                    "Room", "Retrofit", "Firebase", "WebSockets", "AppLovin"
                ),
                featuredProject = Project(
                    title = "DukaanDesk",
                    description = "Shop management application built with Jetpack Compose, MVVM architecture, and Firebase for real-time synchronization.",
                    tags = listOf("Compose", "Firebase", "MVVM", "StateFlow")
                ),
                recentAchievement = Achievement(
                    title = "AdJump SDK",
                    date = "Jan 2024",
                    description = "Developed and maintained a production Android Offerwall SDK integrated across 8+ consumer applications."
                ),
                githubStats = GitHubStats(
                    repositories = 24,
                    stars = 156,
                    followers = 42,
                    contributions = 1250
                )
            )
        }
    }
}
