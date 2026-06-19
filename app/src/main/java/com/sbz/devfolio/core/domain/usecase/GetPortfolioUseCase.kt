package com.sbz.devfolio.core.domain.usecase

import com.sbz.devfolio.core.network.model.PortfolioResponse
import com.sbz.devfolio.core.network.repository.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPortfolioUseCase(
    private val repository: PortfolioRepository
) {
    operator fun invoke(): Flow<Result<PortfolioResponse>> = flow {
        emit(repository.getPortfolioData())
    }
}
