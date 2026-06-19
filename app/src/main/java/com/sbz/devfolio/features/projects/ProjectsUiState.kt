package com.sbz.devfolio.features.projects

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val viewUrl: String? = null,
    val githubUrl: String? = null
)

data class ProjectsUiState(
    val allProjects: List<Project> = emptyList(),
    val filteredProjects: List<Project> = emptyList(),
    val availableTags: List<String> = emptyList(),
    val selectedTag: String? = null
)
