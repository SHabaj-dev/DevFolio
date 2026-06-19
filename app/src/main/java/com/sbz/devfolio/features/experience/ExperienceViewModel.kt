package com.sbz.devfolio.features.experience

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExperienceViewModel : ViewModel() {
    private val mockExperiences = listOf(
        Experience(
            id = "1",
            title = "Software Development Engineer - 1",
            company = "Leadmint Technologies",
            period = "Jan 2024 - Jun 2026",
            description = "Developed and maintained a production Android Offerwall SDK integrated across 8+ consumer applications. Built real-time multiplayer Ludo application supporting WebSockets, and developed a gamified Android learning application using Kotlin and MVVM.",
            isCurrent = true
        ),
        Experience(
            id = "2",
            title = "Junior Android Developer",
            company = "Leadmint Technologies",
            period = "Oct 2023 - Dec 2023",
            description = "Developed and maintained reward-based consumer applications (10L+ downloads). Integrated backend APIs, wallets, and AppLovin/ironSource monetization SDKs."
        ),
        Experience(
            id = "3",
            title = "Android Developer Intern",
            company = "Leadmint Technologies",
            period = "Jul 2023 - Sep 2023",
            description = "Developed Android UI screens and user-facing features using Java and XML. Implemented a Spin Wheel reward feature to improve user engagement."
        )
    )

    private val mockEducation = listOf(
        Experience(
            id = "e1",
            title = "Master of Computer Applications (MCA)",
            company = "Galgotias University",
            period = "2022 - 2024",
            description = "Greater Noida"
        ),
        Experience(
            id = "e2",
            title = "Bachelor of Computer Applications (BCA)",
            company = "Integral University",
            period = "2019 - 2022",
            description = "Lucknow"
        )
    )

    private val _uiState = MutableStateFlow(
        ExperienceUiState(
            experiences = mockExperiences,
            education = mockEducation
        )
    )
    val uiState: StateFlow<ExperienceUiState> = _uiState.asStateFlow()
}
