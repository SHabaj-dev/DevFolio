package com.sbz.devfolio.features.projects

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProjectsViewModel : ViewModel() {
    private val mockProjects = listOf(
        Project(
            id = "1",
            title = "DukaanDesk",
            description = "Shop management application built with Jetpack Compose, MVVM architecture, and Firebase for real-time synchronization.",
            tags = listOf("Compose", "Firebase", "MVVM", "StateFlow")
        ),
        Project(
            id = "2",
            title = "2048 Game",
            description = "Modern Android implementation of the 2048 puzzle game using Jetpack Compose. Includes game-state management and undo functionality.",
            tags = listOf("Compose", "Game", "MVVM")
        ),
        Project(
            id = "3",
            title = "AdJump Android SDK",
            description = "Offerwall & Reward Monetization Platform. Integrated across 8+ consumer applications.",
            tags = listOf("SDK", "Retrofit", "Monetization")
        ),
        Project(
            id = "4",
            title = "Ludo Money",
            description = "Real-Time Multiplayer Gaming Platform supporting 2, 3, and 4-player game modes using WebSockets.",
            tags = listOf("WebSockets", "Multiplayer", "Gaming")
        ),
        Project(
            id = "5",
            title = "BrainZ",
            description = "Gamified Reward-Based Learning Application using Kotlin and MVVM architecture.",
            tags = listOf("Kotlin", "Firebase", "Coroutines")
        )
    )

    private val _uiState = MutableStateFlow(
        ProjectsUiState(
            allProjects = mockProjects,
            filteredProjects = mockProjects,
            availableTags = mockProjects.flatMap { it.tags }.distinct().sorted()
        )
    )
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    fun filterByTag(tag: String?) {
        _uiState.update { state ->
            val filtered = if (tag == null) {
                state.allProjects
            } else {
                state.allProjects.filter { it.tags.contains(tag) }
            }
            state.copy(
                filteredProjects = filtered,
                selectedTag = tag
            )
        }
    }
}
