package com.sbz.devfolio.features.experience

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbz.devfolio.DevFolioApplication
import com.sbz.devfolio.core.designsystem.components.ClayLoadingState
import com.sbz.devfolio.core.designsystem.components.ClayErrorState
import com.sbz.devfolio.core.designsystem.components.ClaySectionHeader
import com.sbz.devfolio.core.designsystem.components.ClayTimelineCard
import com.sbz.devfolio.core.domain.model.PortfolioUiState
import com.sbz.devfolio.core.network.model.PortfolioResponse

@Composable
fun ExperienceScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as DevFolioApplication
    val viewModel: ExperienceViewModel = viewModel(
        factory = ExperienceViewModel.provideFactory(app.container.getPortfolioUseCase)
    )
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is PortfolioUiState.Loading -> {
            ClayLoadingState(message = "Loading Experience...")
        }
        is PortfolioUiState.Error -> {
            ClayErrorState(
                message = state.message,
                onRetry = { viewModel.loadPortfolio() }
            )
        }
        is PortfolioUiState.Success -> {
            ExperienceContent(uiData = state.data, modifier = modifier)
        }
    }
}

@Composable
fun ExperienceContent(
    uiData: PortfolioResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ClaySectionHeader(
            title = "Experience",
            subtitle = "My career path",
            icon = Icons.Default.Work,
            showActions = false
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            item {
                Text(
                    text = "Work Experience",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                )
            }

            itemsIndexed(uiData.experience) { index, exp ->
                val description = buildString {
                    exp.projects?.let { projects ->
                        append("Projects: ${projects.joinToString(", ")}\n\n")
                    }
                    append("• " + exp.highlights.joinToString("\n• "))
                }
                
                ClayTimelineCard(
                    title = exp.role,
                    subtitle = exp.company,
                    period = "${exp.startDate} - ${exp.endDate}",
                    description = description.trim(),
                    isFirst = index == 0,
                    isLast = index == uiData.experience.size - 1,
                    isActive = exp.endDate.equals("Present", ignoreCase = true)
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Education",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                )
            }

            itemsIndexed(uiData.education) { index, edu ->
                ClayTimelineCard(
                    title = edu.degree,
                    subtitle = edu.institution,
                    period = edu.duration,
                    description = "",
                    isFirst = index == 0,
                    isLast = index == uiData.education.size - 1,
                    isActive = false
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(100.dp)) // padding for bottom nav
            }
        }
    }
}
