package com.sbz.devfolio.features.home

data class Project(
    val title: String,
    val description: String,
    val tags: List<String>,
    val viewUrl: String = "",
    val githubUrl: String = ""
)

data class Achievement(
    val title: String,
    val date: String,
    val description: String
)

data class GitHubStats(
    val repositories: Int,
    val stars: Int,
    val followers: Int,
    val contributions: Int
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val title: String = "",
    val description: String = "",
    val projectsCompleted: Int = 0,
    val yearsExperience: Int = 0,
    val technologiesUsed: Int = 0,
    val githubRepositories: Int = 0,
    val techStack: List<String> = emptyList(),
    val featuredProject: Project? = null,
    val recentAchievement: Achievement? = null,
    val githubStats: GitHubStats? = null
)
