package com.sbz.devfolio

import android.os.Bundle
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sbz.devfolio.features.main.MainScreen
import com.sbz.devfolio.features.splash.SplashRoute
import com.sbz.devfolio.ui.theme.DevFolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // edge to edge is handled inside setContent to respect manual theme toggle
        setContent {
            var userDarkTheme by remember { mutableStateOf<Boolean?>(null) }
            val isSystemDark = isSystemInDarkTheme()
            val isDarkTheme = userDarkTheme ?: isSystemDark

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { isDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { isDarkTheme }
                )
                onDispose {}
            }

            DevFolioTheme(
                darkTheme = isDarkTheme,
                onThemeToggle = { userDarkTheme = !isDarkTheme }
            ) {
                var showSplash by remember { mutableStateOf(true) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (showSplash) {
                        SplashRoute(onSplashComplete = { showSplash = false })
                    } else {
                        MainScreen()
                    }
                }
            }
        }
    }
}