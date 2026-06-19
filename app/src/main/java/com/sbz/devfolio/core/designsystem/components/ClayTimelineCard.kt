package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import com.sbz.devfolio.ui.theme.PrimaryColor

@Composable
fun ClayTimelineCard(
    title: String,
    subtitle: String,
    period: String,
    description: String,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    isActive: Boolean = false,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = LocalThemeIsDark.current
    val lineColor = if (isDarkTheme) Color.White.copy(alpha = 0.2f) else Color.Black.copy(alpha = 0.1f)
    val dotColor = if (isActive) PrimaryColor else (if (isDarkTheme) Color.White.copy(alpha = 0.5f) else Color.Gray)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val lineX = 16.dp.toPx()
                val dotY = 32.dp.toPx() // Align dot with the title roughly
                if (!isFirst) {
                    drawLine(
                        color = lineColor,
                        start = Offset(lineX, 0f),
                        end = Offset(lineX, dotY),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                if (!isLast) {
                    drawLine(
                        color = lineColor,
                        start = Offset(lineX, dotY),
                        end = Offset(lineX, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                drawCircle(
                    color = dotColor,
                    radius = 6.dp.toPx(),
                    center = Offset(lineX, dotY)
                )
            }
    ) {
        Spacer(modifier = Modifier.width(48.dp)) // Space for timeline line and padding

        // Content Card
        ClayCard(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 24.dp, end = 24.dp),
            elevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = period,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayTimelineCardPreview() {
    DevFolioTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                ClayTimelineCard(
                    title = "Senior Android Developer",
                    subtitle = "Tech Innovations Inc.",
                    period = "2022 - Present",
                    description = "Lead the Android team in developing scalable apps.",
                    isFirst = true,
                    isActive = true
                )
                ClayTimelineCard(
                    title = "Android Developer",
                    subtitle = "Appify Solutions",
                    period = "2019 - 2022",
                    description = "Developed and maintained e-commerce applications.",
                    isLast = true
                )
            }
        }
    }
}
