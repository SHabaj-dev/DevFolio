package com.sbz.devfolio.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class SplashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        startSyncSimulation()
    }

    private fun startSyncSimulation() {
        viewModelScope.launch {
            val statuses = listOf(
                0f to "Initializing Project...",
                25f to "Downloading Dependencies...",
                50f to "Syncing Gradle...",
                75f to "Building Modules...",
                95f to "Ready to Launch..."
            )

            var currentProgress = 0f
            while (currentProgress < 100f) {
                delay((Random.nextFloat() * 100 + 50).toLong())
                val increment = Random.nextFloat() * 2f + 0.5f
                currentProgress = (currentProgress + increment).coerceAtMost(100f)

                val currentStatus = statuses.lastOrNull { currentProgress >= it.first }?.second ?: "Initializing Project..."

                _uiState.update {
                    it.copy(
                        progress = currentProgress,
                        statusText = currentStatus
                    )
                }
            }

            delay(600) // Pause at 100%
            _uiState.update { it.copy(isComplete = true) }
        }
    }
}
