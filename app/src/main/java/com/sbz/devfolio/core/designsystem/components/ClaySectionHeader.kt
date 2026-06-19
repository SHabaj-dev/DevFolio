package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import com.sbz.devfolio.ui.theme.LocalThemeToggle
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.PrimaryColor

@Composable
fun ClaySectionHeader(
    modifier: Modifier = Modifier,
    title: String = "Welcome Back",
    subtitle: String = "Hello 👋",
    icon: ImageVector? = null,
    showActions: Boolean = true,
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PrimaryColor,
                    modifier = Modifier.padding(end = 12.dp).size(28.dp)
                )
            }
            Column {
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (showActions) {
            Row {
                ClayIconButton(
                    icon = if (LocalThemeIsDark.current) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                    contentDescription = "Toggle Theme",
                    onClick = LocalThemeToggle.current
                )
            }
        }
    }
}

@Composable
fun ClayIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = LocalThemeIsDark.current
    val bgColor = if (isDarkTheme) Color(0xFF1E1E24) else Color(0xFFF0F4F8)

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .claySurface(
                shape = CircleShape,
                backgroundColor = bgColor,
                elevation = 8.dp,
                isDarkTheme = isDarkTheme
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = PrimaryColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClaySectionHeaderPreview() {
    DevFolioTheme {
        ClaySectionHeader()
    }
}
