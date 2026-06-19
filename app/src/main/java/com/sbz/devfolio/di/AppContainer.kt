package com.sbz.devfolio.di

import com.sbz.devfolio.core.domain.usecase.GetPortfolioUseCase
import com.sbz.devfolio.core.network.api.PortfolioApi
import com.sbz.devfolio.core.network.repository.PortfolioRepository
import com.sbz.devfolio.core.network.repository.PortfolioRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val getPortfolioUseCase: GetPortfolioUseCase
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://raw.githubusercontent.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PortfolioApi by lazy {
        retrofit.create(PortfolioApi::class.java)
    }

    private val portfolioRepository: PortfolioRepository by lazy {
        PortfolioRepositoryImpl(retrofitService)
    }

    override val getPortfolioUseCase: GetPortfolioUseCase by lazy {
        GetPortfolioUseCase(portfolioRepository)
    }
}
