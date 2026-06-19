package com.sbz.devfolio.features.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import com.sbz.devfolio.ui.theme.LocalThemeIsDark
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import com.sbz.devfolio.core.designsystem.components.BackgroundGlow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbz.devfolio.core.designsystem.components.ClayAchievementCard

import com.sbz.devfolio.core.designsystem.components.ClayGitHubCard
import com.sbz.devfolio.core.designsystem.components.ClayHeroCard
import com.sbz.devfolio.core.designsystem.components.ClayProjectCard
import com.sbz.devfolio.core.designsystem.components.ClaySectionHeader
import com.sbz.devfolio.core.designsystem.components.ClayStatCard
import com.sbz.devfolio.core.designsystem.components.ClayTechChip
import com.sbz.devfolio.ui.theme.DevFolioTheme
import com.sbz.devfolio.core.utils.ResumeDownloader
import kotlinx.coroutines.delay

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(uiState = uiState, modifier = modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = LocalThemeIsDark.current
    val bgColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFE2E8F0)
    val context = LocalContext.current

    // Staggered animation triggers
    var showHeader by remember { mutableStateOf(false) }
    var showHero by remember { mutableStateOf(false) }
    var showStats by remember { mutableStateOf(false) }
    var showTech by remember { mutableStateOf(false) }
    var showFeatured by remember { mutableStateOf(false) }
    var showAchievement by remember { mutableStateOf(false) }
    var showGithub by remember { mutableStateOf(false) }
    var showCta by remember { mutableStateOf(false) }

    var showContactSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100); showHeader = true
        delay(150); showHero = true
        delay(150); showStats = true
        delay(150); showTech = true
        delay(150); showFeatured = true
        delay(150); showAchievement = true
        delay(150); showGithub = true
        delay(150); showCta = true
    }

    if (showContactSheet) {
        com.sbz.devfolio.core.designsystem.components.ContactBottomSheet(
            onDismissRequest = { showContactSheet = false }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding() + 16.dp,
            bottom = 120.dp
        )
    ) {
        // 1. Welcome Header
        item {
            AnimatedSection(visible = showHeader) {
                ClaySectionHeader()
            }
        }

        // 2. Hero Section
        item {
            AnimatedSection(visible = showHero) {
                Box(contentAlignment = Alignment.Center) {
                    BackgroundGlow(
                        color = Color(0xFF3DDC84),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        alpha = 0.08f
                    )
                    ClayHeroCard(
                        name = uiState.name,
                        title = uiState.title,
                        description = uiState.description,
                        onDownloadResumeClick = { ResumeDownloader.downloadAndOpenResume(context) },
                        onContactMeClick = { showContactSheet = true }
                    )
                }
            }
        }

        // 3. Quick Stats
        item {
            AnimatedSection(visible = showStats) {
                Box(contentAlignment = Alignment.Center) {
                    BackgroundGlow(
                        color = Color(0xFF4285F4),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        alpha = 0.05f
                    )
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                    SectionTitle("Quick Stats")
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ClayStatCard(
                            value = uiState.projectsCompleted,
                            suffix = "+",
                            label = "Projects",
                            modifier = Modifier.weight(1f),
                            delayMs = 400
                        )
                        ClayStatCard(
                            value = uiState.yearsExperience,
                            suffix = "+",
                            label = "Years",
                            modifier = Modifier.weight(1f),
                            delayMs = 500
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ClayStatCard(
                            value = uiState.technologiesUsed,
                            suffix = "+",
                            label = "Techs",
                            modifier = Modifier.weight(1f),
                            delayMs = 600
                        )
                        ClayStatCard(
                            value = uiState.githubRepositories,
                            suffix = "+",
                            label = "Repos",
                            modifier = Modifier.weight(1f),
                            delayMs = 700
                        )
                    }
                }
            }
        }
    }

        // 4. Tech Stack
        item {
            AnimatedSection(visible = showTech) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                    SectionTitle("Tech Stack")
                    Spacer(modifier = Modifier.height(16.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uiState.techStack.forEach { tech ->
                            ClayTechChip(text = tech)
                        }
                    }
                }
            }
        }

        // 5. Featured Project
        if (uiState.featuredProject != null) {
            item {
                AnimatedSection(visible = showFeatured) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                        SectionTitle("Featured Project")
                        Spacer(modifier = Modifier.height(16.dp))
                        ClayProjectCard(
                            title = uiState.featuredProject.title,
                            description = uiState.featuredProject.description,
                            tags = uiState.featuredProject.tags,
                            onViewClick = { },
                            onGithubClick = { }
                        )
                    }
                }
            }
        }

        // 6. Recent Achievement
        if (uiState.recentAchievement != null) {
            item {
                AnimatedSection(visible = showAchievement) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                        SectionTitle("Recent Achievement")
                        Spacer(modifier = Modifier.height(16.dp))
                        ClayAchievementCard(
                            title = uiState.recentAchievement.title,
                            date = uiState.recentAchievement.date,
                            description = uiState.recentAchievement.description
                        )
                    }
                }
            }
        }

        // 7. GitHub Activity
        if (uiState.githubStats != null) {
            item {
                AnimatedSection(visible = showGithub) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                        SectionTitle("GitHub Activity")
                        Spacer(modifier = Modifier.height(16.dp))
                        ClayGitHubCard(
                            repositories = uiState.githubStats.repositories,
                            stars = uiState.githubStats.stars,
                            followers = uiState.githubStats.followers,
                            contributions = uiState.githubStats.contributions
                        )
                    }
                }
            }
        }


    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
fun AnimatedSection(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
            initialOffsetY = { 100 },
            animationSpec = tween(600)
        )
    ) {
        content()
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun HomeScreenPreview() {
    DevFolioTheme {
        HomeRoute()
    }
}
