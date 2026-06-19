package com.sbz.devfolio.core.network.repository

import com.sbz.devfolio.core.network.model.PortfolioResponse

interface PortfolioRepository {
    suspend fun getPortfolioData(): Result<PortfolioResponse>
}
