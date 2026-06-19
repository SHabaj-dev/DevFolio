package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbz.devfolio.R
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import com.sbz.devfolio.ui.theme.PrimaryColor

@Composable
fun ClayAvatarContainer(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "avatarFloat")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "avatarFloatOffset"
    )

    val isDarkTheme = LocalThemeIsDark.current
    val glowColor = if (isDarkTheme) PrimaryColor.copy(alpha = 0.5f) else PrimaryColor.copy(alpha = 0.6f)

    Box(
        modifier = modifier
            .offset(y = floatOffset.dp)
            .size(140.dp), // Size enough to hold avatar + glow
        contentAlignment = Alignment.Center
    ) {
        // Ambient Glow behind the avatar drawn as a perfect circle gradient
        // This avoids the square clipping artifacts caused by the blur modifier
        Canvas(modifier = Modifier.size(130.dp)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(glowColor, Color.Transparent),
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.width / 2
                )
            )
        }

        // The actual Avatar
        Box(
            modifier = Modifier
                .size(100.dp)
                .claySurface(
                    shape = CircleShape,
                    elevation = 16.dp,
                    isDarkTheme = isDarkTheme
                )
                .border(4.dp, PrimaryColor, CircleShape)
                .clip(CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_avatra_without_logos),
                    contentDescription = "Profile Avatar",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayAvatarContainerPreview() {
    DevFolioTheme {
        Box(modifier = Modifier.padding(32.dp)) {
            ClayAvatarContainer()
        }
    }
}
