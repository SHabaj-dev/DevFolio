package com.sbz.devfolio.core.domain.model

import com.sbz.devfolio.core.network.model.PortfolioResponse

sealed interface PortfolioUiState {
    data object Loading : PortfolioUiState
    data class Success(val data: PortfolioResponse) : PortfolioUiState
    data class Error(val message: String) : PortfolioUiState
}
