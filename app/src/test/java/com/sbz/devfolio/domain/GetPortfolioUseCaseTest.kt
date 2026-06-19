package com.sbz.devfolio.domain

import com.sbz.devfolio.core.domain.usecase.GetPortfolioUseCase
import com.sbz.devfolio.core.network.model.Achievement
import com.sbz.devfolio.core.network.model.Education
import com.sbz.devfolio.core.network.model.PortfolioResponse
import com.sbz.devfolio.core.network.model.Profile
import com.sbz.devfolio.core.network.model.Skills
import com.sbz.devfolio.core.network.model.SocialLinks
import com.sbz.devfolio.core.network.model.Stats
import com.sbz.devfolio.core.network.repository.PortfolioRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [GetPortfolioUseCase].
 *
 * Uses a hand-rolled fake repository — no Mockito / MockK needed.
 * The fake is defined as a private inner class so the tests remain self-contained.
 */
class GetPortfolioUseCaseTest {

    // ------------------------------------------------------------------
    // Fake repository implementations (no mocking framework needed)
    // ------------------------------------------------------------------

    private fun fakePortfolioResponse() = PortfolioResponse(
        profile = Profile(
            name            = "Shabaj Ansari",
            title           = "Android Software Engineer",
            location        = "Noida, India",
            phone           = "+91 0000000000",
            email           = "test@example.com",
            summary         = "Test bio",
            experienceYears = 3,
            avatarUrl       = "",
            resumeUrl       = "",
            linkedinUrl     = "",
            githubUrl       = "",
            tagline         = "Building scalable Android apps",
            availableFor    = listOf("Android Development", "SDK Development")
        ),
        stats        = Stats(yearsExperience = 3, projectsCompleted = 10, technologiesUsed = 15, appsWorkedOn = 8),
        skills       = Skills(
            languages    = listOf("Kotlin", "Java"),
            android      = listOf("Jetpack Compose", "MVVM"),
            networking   = listOf("Retrofit"),
            firebase     = listOf("Firestore"),
            monetization = listOf("AdMob"),
            tools        = listOf("Git", "Android Studio")
        ),
        experience      = emptyList(),
        featuredProject = null,
        projects        = emptyList(),
        achievements    = emptyList(),
        education       = emptyList(),
        socialLinks     = SocialLinks("test@example.com", "+91 0000000000", "", "")
    )

    /** Returns the fake response successfully. */
    private inner class FakeSuccessRepository : PortfolioRepository {
        override suspend fun getPortfolioData(): Result<PortfolioResponse> =
            Result.success(fakePortfolioResponse())
    }

    /** Always fails with a given exception. */
    private inner class FakeFailureRepository(
        private val exception: Exception = Exception("Network error")
    ) : PortfolioRepository {
        override suspend fun getPortfolioData(): Result<PortfolioResponse> =
            Result.failure(exception)
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    fun `invoke emits success Result when repository succeeds`() = runTest {
        val useCase = GetPortfolioUseCase(FakeSuccessRepository())

        val result = useCase().first()

        assertTrue(result.isSuccess)
        assertEquals("Shabaj Ansari", result.getOrNull()?.profile?.name)
    }

    @Test
    fun `invoke emits correct stats on success`() = runTest {
        val useCase = GetPortfolioUseCase(FakeSuccessRepository())

        val result = useCase().first()
        val stats  = result.getOrNull()?.stats

        assertEquals(10, stats?.projectsCompleted)
        assertEquals(3, stats?.yearsExperience)
        assertEquals(15, stats?.technologiesUsed)
    }

    @Test
    fun `invoke emits failure Result when repository throws`() = runTest {
        val expectedMessage = "Network error"
        val useCase = GetPortfolioUseCase(FakeFailureRepository(Exception(expectedMessage)))

        val result = useCase().first()

        assertTrue(result.isFailure)
        assertEquals(expectedMessage, result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke emits failure with correct exception type`() = runTest {
        val cause   = RuntimeException("Timeout")
        val useCase = GetPortfolioUseCase(FakeFailureRepository(cause))

        val result = useCase().first()

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }

    @Test
    fun `invoke returns a flow that emits exactly one item`() = runTest {
        val useCase = GetPortfolioUseCase(FakeSuccessRepository())
        val emissions = mutableListOf<Result<PortfolioResponse>>()

        useCase().collect { emissions.add(it) }

        assertEquals(1, emissions.size)
    }

    @Test
    fun `profile availableFor list is preserved correctly`() = runTest {
        val useCase = GetPortfolioUseCase(FakeSuccessRepository())

        val result  = useCase().first()
        val available = result.getOrNull()?.profile?.availableFor

        assertEquals(listOf("Android Development", "SDK Development"), available)
    }
}
