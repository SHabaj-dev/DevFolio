package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.PrimaryColor

@Composable
fun ClayHeroCard(
    name: String,
    title: String,
    description: String,
    onDownloadResumeClick: () -> Unit,
    onContactMeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp, start = 24.dp, end = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Soft Glows behind the card
        BackgroundGlow(
            color = Color(0xFF3DDC84), // Android Green
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-60).dp, y = 20.dp),
            alpha = 0.08f
        )
        BackgroundGlow(
            color = Color(0xFF4285F4), // Blue
            modifier = Modifier
                .size(200.dp)
                .offset(x = 60.dp, y = 100.dp),
            alpha = 0.08f
        )

        ClayCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp), // Push card down so avatar can overlap
            shape = RoundedCornerShape(32.dp),
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ClayButton(
                        text = "Download CV",
                        onClick = onDownloadResumeClick,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    ClayButton(
                        text = "Contact Me",
                        onClick = onContactMeClick,
                        backgroundColor = PrimaryColor,
                        textColor = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // The Floating Avatar Container handles its own animation
        ClayAvatarContainer(
            modifier = Modifier.offset(y = (-10).dp)
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayHeroCardPreview() {
    DevFolioTheme {
        ClayHeroCard(
            name = "Shabaj",
            title = "Android Developer",
            description = "Building scalable Android applications with Kotlin & Jetpack Compose.",
            onDownloadResumeClick = {},
            onContactMeClick = {}
        )
    }
}
