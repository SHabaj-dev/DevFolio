package com.sbz.devfolio.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sbz.devfolio.core.domain.model.PortfolioUiState
import com.sbz.devfolio.core.domain.usecase.GetPortfolioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PortfolioUiState>(PortfolioUiState.Loading)
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()

    init {
        loadPortfolio()
    }

    fun loadPortfolio() {
        _uiState.value = PortfolioUiState.Loading
        viewModelScope.launch {
            getPortfolioUseCase().collect { result ->
                result.fold(
                    onSuccess = { _uiState.value = PortfolioUiState.Success(it) },
                    onFailure = { _uiState.value = PortfolioUiState.Error(it.message ?: "Unknown error") }
                )
            }
        }
    }

    companion object {
        fun provideFactory(useCase: GetPortfolioUseCase): ViewModelProvider.Factory = 
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProfileViewModel(useCase) as T
                }
            }
    }
}
