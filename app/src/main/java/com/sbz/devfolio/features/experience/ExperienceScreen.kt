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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbz.devfolio.core.designsystem.components.ClaySectionHeader
import com.sbz.devfolio.core.designsystem.components.ClayTimelineCard

@Composable
fun ExperienceScreen(
    viewModel: ExperienceViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
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

            itemsIndexed(uiState.experiences) { index, exp ->
                ClayTimelineCard(
                    title = exp.title,
                    subtitle = exp.company,
                    period = exp.period,
                    description = exp.description,
                    isFirst = index == 0,
                    isLast = index == uiState.experiences.size - 1,
                    isActive = exp.isCurrent
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

            itemsIndexed(uiState.education) { index, edu ->
                ClayTimelineCard(
                    title = edu.title,
                    subtitle = edu.company,
                    period = edu.period,
                    description = edu.description,
                    isFirst = index == 0,
                    isLast = index == uiState.education.size - 1,
                    isActive = edu.isCurrent
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(100.dp)) // padding for bottom nav
            }
        }
    }
}
