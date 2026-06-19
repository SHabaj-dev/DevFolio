package com.sbz.devfolio.core.network.model

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(
    val profile: Profile,
    val stats: Stats,
    val skills: Skills,
    val experience: List<Experience>,
    val featuredProject: Project?,
    val projects: List<Project>,
    val achievements: List<Achievement>,
    val education: List<Education>,
    val socialLinks: SocialLinks
)

data class Profile(
    val name: String,
    val title: String,
    val location: String,
    val phone: String,
    val email: String,
    val summary: String,
    val experienceYears: Int,
    val avatarUrl: String,
    val resumeUrl: String,
    val linkedinUrl: String,
    val githubUrl: String,
    val tagline: String,
    val availableFor: List<String>
)

data class Stats(
    val yearsExperience: Int,
    val projectsCompleted: Int,
    val technologiesUsed: Int,
    val appsWorkedOn: Int
)

data class Skills(
    val languages: List<String>,
    val android: List<String>,
    val networking: List<String>,
    val firebase: List<String>,
    val monetization: List<String>,
    val tools: List<String>
)

data class Experience(
    val company: String,
    val role: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val projects: List<String>? = null,
    val highlights: List<String>
)

data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>,
    val featured: Boolean? = false,
    val githubUrl: String? = null,
    val playStoreUrl: String? = null,
    val imageUrl: String? = null
)

data class Achievement(
    val title: String,
    val description: String
)

data class Education(
    val degree: String,
    val institution: String,
    val duration: String
)

data class SocialLinks(
    val email: String,
    val phone: String,
    val linkedin: String,
    val github: String
)
