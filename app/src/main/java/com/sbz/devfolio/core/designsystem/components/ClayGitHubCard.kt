package com.sbz.devfolio.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.ui.theme.PrimaryColor

@Composable
fun ClayGitHubCard(
    repositories: Int,
    stars: Int,
    followers: Int,
    contributions: Int,
    modifier: Modifier = Modifier
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GitHubStatItem(icon = Icons.Rounded.Code, label = "Repositories", value = repositories.toString())
                GitHubStatItem(icon = Icons.Rounded.Star, label = "Stars", value = stars.toString())
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GitHubStatItem(icon = Icons.Rounded.People, label = "Followers", value = followers.toString())
                GitHubStatItem(icon = Icons.Rounded.Timeline, label = "Contributions", value = contributions.toString())
            }
        }
    }
}

@Composable
private fun GitHubStatItem(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.width(120.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = PrimaryColor,
            modifier = Modifier.padding(end = 12.dp)
        )
        Column {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun ClayGitHubCardPreview() {
    DevFolioTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ClayGitHubCard(
                repositories = 45,
                stars = 128,
                followers = 64,
                contributions = 1450
            )
        }
    }
}
