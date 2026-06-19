package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.LocalThemeIsDark

@Composable
fun ClayTechChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = 0.5f),
        label = "chipScale"
    )

    val isDarkTheme = LocalThemeIsDark.current

    Box(
        modifier = modifier
            .scale(scale)
            .let {
                if (isPressed) {
                    it.clayPressed(
                        shape = CircleShape,
                        isDarkTheme = isDarkTheme
                    )
                } else {
                    it.claySurface(
                        shape = CircleShape,
                        elevation = 6.dp,
                        isDarkTheme = isDarkTheme
                    )
                }
            }
            .clickable(
                enabled = onClick != null,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick?.invoke() }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayTechChipPreview() {
    DevFolioTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ClayTechChip(text = "Jetpack Compose")
        }
    }
}
