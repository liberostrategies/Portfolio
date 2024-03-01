package com.liberostrategies.pinkyportfolio.model

import com.google.common.truth.Truth.assertThat
import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.domain.usecases.MatchQualificationsWithSkillsUseCase
import com.liberostrategies.pinkyportfolio.domain.usecases.ReadJobQualificationUseCase
import com.liberostrategies.pinkyportfolio.screens.JobQualifications
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class JobQualificationUseCaseTest {
    private val jobQualificationsRepo: IJobQualificationRepository = mockk(relaxed = true) // Return default value.

    private val validLanguageQualification = "Qt"

    @Test
    fun `Read - Category of qualification`() = runBlocking {
        coEvery {
            jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
            jobQualificationsRepo.readQualification(any(), validLanguageQualification)
        } returns Unit
        val useCase = ReadJobQualificationUseCase(jobQualificationsRepo)
        val actual = useCase(JobQualifications.LANGUAGES, validLanguageQualification)
        coVerify(atLeast = 1){
            jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        }
        confirmVerified(jobQualificationsRepo)
    }

    @Test
    fun `Match job categories with resume skills`() = runBlocking {
        every {
            jobQualificationsRepo.matchQualificationsWithSkills(any())
        } returns 75
        val useCase = MatchQualificationsWithSkillsUseCase(jobQualificationsRepo)
        val actual = useCase(3)
        verify {
            jobQualificationsRepo.matchQualificationsWithSkills(3)
        }
        confirmVerified(jobQualificationsRepo)
        assertThat(actual.data).isEqualTo(75)
    }
}