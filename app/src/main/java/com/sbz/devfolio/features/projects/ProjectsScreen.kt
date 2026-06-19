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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbz.devfolio.core.designsystem.components.ClayProjectCard
import com.sbz.devfolio.core.designsystem.components.ClaySectionHeader
import com.sbz.devfolio.core.designsystem.components.ClayTechChip

@Composable
fun ProjectsScreen(
    viewModel: ProjectsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
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
                    onClick = { viewModel.filterByTag(null) }
                )
            }
            items(uiState.availableTags) { tag ->
                ClayTechChip(
                    text = tag,
                    onClick = { viewModel.filterByTag(tag) }
                )
            }
        }

        // Projects Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.filteredProjects, key = { it.id }) { project ->
                ClayProjectCard(
                    title = project.title,
                    description = project.description,
                    tags = project.tags,
                    onViewClick = { /* Handle view click */ },
                    onGithubClick = { /* Handle github click */ }
                )
            }
        }
    }
}
