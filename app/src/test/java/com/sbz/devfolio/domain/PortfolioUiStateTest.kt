package com.sbz.devfolio.domain

import com.sbz.devfolio.core.domain.model.PortfolioUiState
import com.sbz.devfolio.core.network.model.Achievement
import com.sbz.devfolio.core.network.model.Education
import com.sbz.devfolio.core.network.model.PortfolioResponse
import com.sbz.devfolio.core.network.model.Profile
import com.sbz.devfolio.core.network.model.Skills
import com.sbz.devfolio.core.network.model.SocialLinks
import com.sbz.devfolio.core.network.model.Stats
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [PortfolioUiState] sealed interface.
 *
 * Verifies that each state variant carries the correct data and that the
 * type system correctly distinguishes between them.
 */
class PortfolioUiStateTest {

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private fun fakePortfolioResponse() = PortfolioResponse(
        profile = Profile(
            name           = "Shabaj Ansari",
            title          = "Android Software Engineer",
            location       = "Noida, India",
            phone          = "+91 0000000000",
            email          = "test@example.com",
            summary        = "Test summary",
            experienceYears = 3,
            avatarUrl      = "",
            resumeUrl      = "",
            linkedinUrl    = "",
            githubUrl      = "",
            tagline        = "Building scalable apps",
            availableFor   = listOf("Android Development")
        ),
        stats        = Stats(3, 10, 15, 8),
        skills       = Skills(
            languages    = listOf("Kotlin"),
            android      = listOf("Jetpack Compose"),
            networking   = listOf("Retrofit"),
            firebase     = listOf("Firestore"),
            monetization = listOf("AdMob"),
            tools        = listOf("Git")
        ),
        experience      = emptyList(),
        featuredProject = null,
        projects        = emptyList(),
        achievements    = emptyList(),
        education       = emptyList(),
        socialLinks     = SocialLinks("test@example.com", "+91 0000000000", "", "")
    )

    // ------------------------------------------------------------------
    // Loading state
    // ------------------------------------------------------------------

    @Test
    fun `Loading state is a data object`() {
        val state: PortfolioUiState = PortfolioUiState.Loading
        assertTrue(state is PortfolioUiState.Loading)
    }

    @Test
    fun `Loading state equals itself`() {
        assertEquals(PortfolioUiState.Loading, PortfolioUiState.Loading)
    }

    // ------------------------------------------------------------------
    // Success state
    // ------------------------------------------------------------------

    @Test
    fun `Success state carries the correct PortfolioResponse`() {
        val data  = fakePortfolioResponse()
        val state = PortfolioUiState.Success(data)

        assertTrue(state is PortfolioUiState.Success)
        assertEquals("Shabaj Ansari", (state as PortfolioUiState.Success).data.profile.name)
    }

    @Test
    fun `Success state exposes stats correctly`() {
        val data  = fakePortfolioResponse()
        val state = PortfolioUiState.Success(data) as PortfolioUiState.Success

        assertEquals(10, state.data.stats.projectsCompleted)
        assertEquals(3, state.data.stats.yearsExperience)
    }

    @Test
    fun `Two Success states with equal data are equal`() {
        val data = fakePortfolioResponse()
        assertEquals(PortfolioUiState.Success(data), PortfolioUiState.Success(data))
    }

    @Test
    fun `Error state with same message equals itself`() {
        val msg = "Timeout"
        assertEquals(PortfolioUiState.Error(msg), PortfolioUiState.Error(msg))
    }

    @Test
    fun `Error state with different messages are not equal`() {
        val s1 = PortfolioUiState.Error("404")
        val s2 = PortfolioUiState.Error("500")
        assertTrue(s1 != s2)
    }

    // ------------------------------------------------------------------
    // State discrimination
    // ------------------------------------------------------------------

    @Test
    fun `Loading is not Success`() {
        val state: PortfolioUiState = PortfolioUiState.Loading
        assertTrue(state !is PortfolioUiState.Success)
    }

    @Test
    fun `Loading is not Error`() {
        val state: PortfolioUiState = PortfolioUiState.Loading
        assertTrue(state !is PortfolioUiState.Error)
    }

    @Test
    fun `Success is not Error`() {
        val state: PortfolioUiState = PortfolioUiState.Success(fakePortfolioResponse())
        assertTrue(state !is PortfolioUiState.Error)
    }
}
