package com.sbz.devfolio.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sbz.devfolio.ui.theme.LocalThemeIsDark

@Composable
fun ClayNavigationContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val isDarkTheme = LocalThemeIsDark.current
    val containerBgColor = MaterialTheme.colorScheme.background

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .claySurface(
                backgroundColor = containerBgColor,
                isDarkTheme = isDarkTheme
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
