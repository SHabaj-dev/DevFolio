package com.sbz.devfolio.features.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbz.devfolio.DevFolioApplication
import com.sbz.devfolio.core.designsystem.components.ClayLoadingState
import com.sbz.devfolio.core.designsystem.components.ClayErrorState
import com.sbz.devfolio.core.designsystem.components.ClayProjectCard
import com.sbz.devfolio.core.designsystem.components.ClaySectionHeader
import com.sbz.devfolio.core.designsystem.components.ClayTechChip
import com.sbz.devfolio.core.domain.model.PortfolioUiState
import com.sbz.devfolio.core.network.model.PortfolioResponse

@Composable
fun ProjectsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as DevFolioApplication
    val viewModel: ProjectsViewModel = viewModel(
        factory = ProjectsViewModel.provideFactory(app.container.getPortfolioUseCase)
    )
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is PortfolioUiState.Loading -> {
            ClayLoadingState(message = "Fetching Projects...")
        }
        is PortfolioUiState.Error -> {
            ClayErrorState(
                message = state.message,
                onRetry = { viewModel.loadPortfolio() }
            )
        }
        is PortfolioUiState.Success -> {
            ProjectsContent(uiData = state.data, modifier = modifier)
        }
    }
}

@Composable
fun ProjectsContent(
    uiData: PortfolioResponse,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTag by remember { mutableStateOf<String?>(null) }
    
    val availableTags = remember(uiData.projects) {
        uiData.projects.flatMap { it.technologies }.distinct().sorted()
    }
    
    val filteredProjects = remember(uiData.projects, selectedTag) {
        if (selectedTag == null) {
            uiData.projects
        } else {
            uiData.projects.filter { it.technologies.contains(selectedTag) }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ClaySectionHeader(
            title = "Projects",
            subtitle = "My recent work",
            icon = Icons.Default.Code,
            showActions = false
        )

        // Filter Chips
        LazyRow(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ClayTechChip(
                    text = "All",
                    onClick = { selectedTag = null }
                )
            }
            items(availableTags) { tag ->
                ClayTechChip(
                    text = tag,
                    onClick = { selectedTag = tag }
                )
            }
        }

        // Projects Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 120.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredProjects) { project ->
                ClayProjectCard(
                    title = project.title,
                    description = project.description,
                    tags = project.technologies,
                    imageUrl = project.imageUrl,
                    onViewClick = { 
                        project.playStoreUrl?.let { url ->
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
                            context.startActivity(intent)
                        }
                    },
                    onGithubClick = { 
                        project.githubUrl?.let { url ->
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}
