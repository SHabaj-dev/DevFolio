package com.sbz.devfolio.core.network.repository

import com.sbz.devfolio.core.network.api.PortfolioApi
import com.sbz.devfolio.core.network.model.PortfolioResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PortfolioRepositoryImpl(
    private val api: PortfolioApi
) : PortfolioRepository {

    // Prepared for caching later (Room could be injected here)
    private var cachedData: PortfolioResponse? = null

    override suspend fun getPortfolioData(): Result<PortfolioResponse> {
        if (cachedData != null) {
            return Result.success(cachedData!!)
        }
        
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPortfolioData()
                cachedData = response
                Result.success(response)
            } catch (e: Exception) {
                if (cachedData != null) {
                    Result.success(cachedData!!)
                } else {
                    Result.failure(e)
                }
            }
        }
    }
}
