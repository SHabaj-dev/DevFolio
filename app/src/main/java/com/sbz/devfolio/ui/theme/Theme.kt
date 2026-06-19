package com.sbz.devfolio.ui.theme
import android.app.Activity
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = Color(0xFF121212),
    surface = Color(0xFF121212)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = Color(0xFFF0F4F8),
    surface = Color(0xFFF0F4F8)
)



val LocalThemeIsDark = compositionLocalOf { false }
val LocalThemeToggle = staticCompositionLocalOf<() -> Unit> { {} }

@Composable
fun DevFolioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled to preserve the custom Claymorphism color palette
    dynamicColor: Boolean = false,
    onThemeToggle: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalThemeIsDark provides darkTheme,
        LocalThemeToggle provides onThemeToggle
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}