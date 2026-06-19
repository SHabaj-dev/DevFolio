package com.sbz.devfolio.features.experience

data class Experience(
    val id: String,
    val title: String,
    val company: String,
    val period: String,
    val description: String,
    val isCurrent: Boolean = false
)

data class ExperienceUiState(
    val experiences: List<Experience> = emptyList(),
    val education: List<Experience> = emptyList()
)
