package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbz.devfolio.core.navigation.BottomNavScreens
import com.sbz.devfolio.core.navigation.Screen
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import com.sbz.devfolio.ui.theme.PrimaryColor

// Reusable Claymorphism Modifiers extracted to ClayModifiers.kt

@Composable
fun ClayBottomNavigation(
    screens: List<Screen>,
    currentRoute: String?,
    onNavigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = LocalThemeIsDark.current
    val containerBgColor = MaterialTheme.colorScheme.background

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .height(76.dp)
    ) {
        ClayNavigationContainer {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                screens.forEach { screen ->
                    val isSelected = currentRoute == screen.route

                    ClayNavigationItem(
                        screen = screen,
                        isSelected = isSelected,
                        onClick = { onNavigateTo(screen.route) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ClayNavigationItem(
    screen: Screen,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "itemScale"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .scale(scale),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = screen.icon,
            contentDescription = screen.title,
            tint = if (isSelected) PrimaryColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.5f
            ),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayBottomNavigationPreview() {
    var currentRoute by remember { mutableStateOf(Screen.Home.route) }

    DevFolioTheme {
        Box(
            modifier = Modifier
                .background(if (LocalThemeIsDark.current) Color(0xFF121212) else Color(0xFFE2E8F0))
                .padding(16.dp)
        ) {
            ClayBottomNavigation(
                screens = BottomNavScreens,
                currentRoute = currentRoute,
                onNavigateTo = { currentRoute = it }
            )
        }
    }
}
