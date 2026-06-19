package com.sbz.devfolio

import android.app.Application
import com.sbz.devfolio.di.AppContainer
import com.sbz.devfolio.di.DefaultAppContainer

class DevFolioApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
