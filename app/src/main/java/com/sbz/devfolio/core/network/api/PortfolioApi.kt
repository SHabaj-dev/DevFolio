package com.sbz.devfolio.core.network.api

import com.sbz.devfolio.core.network.model.PortfolioResponse
import retrofit2.http.GET

interface PortfolioApi {
    @GET("SHabaj-dev/devfolio-data/refs/heads/main/data.json")
    suspend fun getPortfolioData(): PortfolioResponse
}
