package com.sbz.devfolio.features.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.sbz.devfolio.core.designsystem.components.ClayCard
import com.sbz.devfolio.core.designsystem.components.ClayHeroCard
import com.sbz.devfolio.core.designsystem.components.ClaySectionHeader
import com.sbz.devfolio.core.designsystem.components.ClayTechChip
import com.sbz.devfolio.core.utils.ResumeDownloader

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showContactSheet by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    if (showContactSheet) {
        com.sbz.devfolio.core.designsystem.components.ContactBottomSheet(
            onDismissRequest = { showContactSheet = false }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ClaySectionHeader(
            title = "Profile",
            subtitle = "About me",
            icon = Icons.Default.Person,
            showActions = false
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ClayHeroCard(
                    name = uiState.name,
                    title = uiState.title,
                    description = uiState.bio.take(100) + "...", // short description for hero
                    onDownloadResumeClick = { ResumeDownloader.downloadAndOpenResume(context) },
                    onContactMeClick = { showContactSheet = true }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "About",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ClayCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = uiState.bio,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "Skills",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ClayCard(modifier = Modifier.fillMaxWidth()) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.skills.forEach { skill ->
                            ClayTechChip(text = skill)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Spacer(modifier = Modifier.height(100.dp)) // padding for bottom nav
            }
        }
    }
}
