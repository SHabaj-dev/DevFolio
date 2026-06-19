package com.sbz.devfolio.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Reusable Claymorphism Modifiers
fun Modifier.claySurface(
    shape: Shape = RoundedCornerShape(24.dp),
    backgroundColor: Color? = null,
    elevation: Dp = 12.dp,
    isDarkTheme: Boolean
): Modifier {
    val gradientColors = if (backgroundColor != null && backgroundColor != Color(0xFF1E1E24) && backgroundColor != Color(0xFFF0F4F8)) {
        // If a specific custom background color is forced, use it with a subtle gradient
        listOf(backgroundColor, backgroundColor.copy(alpha = 0.8f))
    } else if (isDarkTheme) {
        listOf(Color(0xFF2B2E38), Color(0xFF1E2128)) // Dark gradient
    } else {
        listOf(Color(0xFFFFFFFF), Color(0xFFE8EDF3)) // Light gradient 145deg
    }

    val lightHighlight = if (isDarkTheme) Color.White.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.9f)
    val darkShadow = if (isDarkTheme) Color.Black.copy(alpha = 0.8f) else Color(0xFF0F172A).copy(alpha = 0.08f)
    val outerShadowColor = if (isDarkTheme) Color.Black.copy(alpha = 0.8f) else Color(0xFF0F172A).copy(alpha = 0.12f)

    return this
        .shadow(
            elevation = elevation,
            shape = shape,
            spotColor = outerShadowColor,
            ambientColor = outerShadowColor
        )
        .background(
            brush = Brush.linearGradient(
                colors = gradientColors
            ),
            shape = shape
        )
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    lightHighlight,
                    Color.Transparent,
                    Color.Transparent,
                    darkShadow
                )
            ),
            shape = shape
        )
}

fun Modifier.clayPressed(
    shape: Shape = CircleShape,
    backgroundColor: Color? = null,
    isDarkTheme: Boolean
): Modifier {
    val gradientColors = if (backgroundColor != null && backgroundColor != Color(0xFF1E1E24) && backgroundColor != Color(0xFFF0F4F8)) {
        listOf(backgroundColor.copy(alpha = 0.8f), backgroundColor)
    } else if (isDarkTheme) {
        listOf(Color(0xFF1E2128), Color(0xFF2B2E38)) // Reverse Dark gradient
    } else {
        listOf(Color(0xFFE8EDF3), Color(0xFFFFFFFF)) // Reverse Light gradient
    }

    val innerDark = if (isDarkTheme) Color.Black.copy(alpha = 0.8f) else Color(0xFF0F172A).copy(alpha = 0.15f)
    val innerLight = if (isDarkTheme) Color.White.copy(alpha = 0.05f) else Color.White.copy(alpha = 0.9f)

    return this
        .background(
            brush = Brush.linearGradient(
                colors = gradientColors
            ),
            shape = shape
        )
        .border(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    innerDark,
                    Color.Transparent,
                    Color.Transparent,
                    innerLight
                )
            ),
            shape = shape
        )
}
