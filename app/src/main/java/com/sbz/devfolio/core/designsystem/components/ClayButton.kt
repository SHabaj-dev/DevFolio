package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.PrimaryColor

/**
 * A reusable button component applying the claymorphism style and pressed animation.
 */
@Composable
fun ClayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color? = null,
    textColor: Color = PrimaryColor,
    elevation: Dp = 8.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "buttonScale"
    )

    val isDarkTheme = LocalThemeIsDark.current
    val defaultBgColor = if (isDarkTheme) Color(0xFF1E1E24) else Color(0xFFF0F4F8)

    Box(
        modifier = modifier
            .scale(scale)
            .let {
                if (isPressed) {
                    it.clayPressed(
                        shape = shape,
                        backgroundColor = backgroundColor ?: defaultBgColor,
                        isDarkTheme = isDarkTheme
                    )
                } else {
                    it.claySurface(
                        shape = shape,
                        backgroundColor = backgroundColor ?: defaultBgColor,
                        elevation = elevation,
                        isDarkTheme = isDarkTheme
                    )
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayButtonPreview() {
    DevFolioTheme {
        Box(modifier = Modifier.padding(32.dp)) {
            ClayButton(
                text = "Click Me",
                onClick = {}
            )
        }
    }
}
