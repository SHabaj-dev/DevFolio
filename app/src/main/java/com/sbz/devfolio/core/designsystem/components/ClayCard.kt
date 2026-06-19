package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sbz.devfolio.ui.theme.DevFolioTheme

/**
 * A highly reusable base card component that applies the Claymorphism surface modifier.
 * Used to wrap content in a 3D clay-like container.
 */
@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    backgroundColor: Color? = null,
    elevation: Dp = 12.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val isDarkTheme = LocalThemeIsDark.current
    val defaultBgColor = if (isDarkTheme) Color(0xFF1E1E24) else Color(0xFFF0F4F8)
    
    Box(
        modifier = modifier.claySurface(
            shape = shape,
            backgroundColor = backgroundColor ?: defaultBgColor,
            elevation = elevation,
            isDarkTheme = isDarkTheme
        ),
        content = content
    )
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayCardPreview() {
    DevFolioTheme {
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ClayCard(modifier = Modifier.size(150.dp)) {
                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Clay Card")
                }
            }
        }
    }
}
