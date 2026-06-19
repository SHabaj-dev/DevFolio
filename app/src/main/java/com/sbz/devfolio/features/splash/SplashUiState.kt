package com.sbz.devfolio.features.splash

data class SplashUiState(
    val progress: Float = 0f,
    val statusText: String = "Initializing Project...",
    val isComplete: Boolean = false
)
