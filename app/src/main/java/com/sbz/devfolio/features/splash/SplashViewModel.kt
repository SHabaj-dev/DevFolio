package com.sbz.devfolio.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sbz.devfolio.core.domain.model.PortfolioUiState
import com.sbz.devfolio.core.domain.usecase.GetPortfolioUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class SplashViewModel(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Start the visual progress bar simulation
            val progressJob = launch {
                val statuses = listOf(
                    0f to "Connecting to server...",
                    25f to "Fetching portfolio data...",
                    50f to "Parsing profile...",
                    75f to "Loading projects...",
                    90f to "Almost ready..."
                )

                var currentProgress = 0f
                while (currentProgress < 95f) { // don't go to 100% until success
                    delay((Random.nextFloat() * 50 + 20).toLong())
                    val increment = Random.nextFloat() * 2f + 0.5f
                    currentProgress = (currentProgress + increment).coerceAtMost(95f)

                    val currentStatus = statuses.lastOrNull { currentProgress >= it.first }?.second ?: "Loading..."

                    _uiState.update {
                        it.copy(
                            progress = currentProgress,
                            statusText = currentStatus
                        )
                    }
                }
            }

            // Simultaneously fetch data
            getPortfolioUseCase().collect { result ->
                result.fold(
                    onSuccess = {
                        progressJob.cancel()
                        // Animate quickly to 100%
                        _uiState.update { 
                            it.copy(progress = 100f, statusText = "Ready to Launch...") 
                        }
                        delay(500)
                        _uiState.update { it.copy(isComplete = true) }
                    },
                    onFailure = { error ->
                        progressJob.cancel()
                        _uiState.update { 
                            it.copy(progress = 100f, statusText = "Failed to load: ${error.message}") 
                        }
                        delay(1000)
                        _uiState.update { it.copy(isComplete = true) } // Proceed anyway, main screen will show Error State
                    }
                )
            }
        }
    }

    companion object {
        fun provideFactory(
            getPortfolioUseCase: GetPortfolioUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SplashViewModel(getPortfolioUseCase) as T
            }
        }
    }
}
