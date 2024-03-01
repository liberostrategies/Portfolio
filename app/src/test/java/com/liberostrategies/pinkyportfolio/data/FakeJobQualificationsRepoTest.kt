package com.liberostrategies.pinkyportfolio.data

import com.liberostrategies.pinkyportfolio.data.repo.JobQualificationsRepo
import com.liberostrategies.pinkyportfolio.data.source.FakeJobQualificationDataSource
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.screens.JobQualifications
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FakeJobQualificationsRepoTest {
    private val jobQualificationsRepo = JobQualificationsRepo(FakeJobQualificationDataSource())

    private val validLanguageQualification = "Kotlin"

    @Test
    fun `Read - Category of qualification`() = runBlocking {
        jobQualificationsRepo.getListQualifications().clear()
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        assertEquals(JobQualifications.LANGUAGES, jobQualificationsRepo.getListQualifications()[0].category)
    }

    @Test
    fun `Read - Qualification for category`() = runBlocking {
        jobQualificationsRepo.getListQualifications().clear()
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        assertEquals(validLanguageQualification, jobQualificationsRepo.getListQualifications()[0].qualification )
    }

    @Test
    fun `Read - Qualification list size`() = runBlocking {
        jobQualificationsRepo.getListQualifications().clear()
        // Initialize with 2 job qualifications.
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, "Java")

        jobQualificationsRepo.readInitialQualificationsSize(jobQualificationsRepo.getListQualifications().size)
        assertTrue { 2 == jobQualificationsRepo.getListQualifications().size }
    }

    @Test
    fun `Calculate percentage of matching selected job qualifications with resume skills`() = runBlocking{
        jobQualificationsRepo.getListQualifications().clear()
        // Job qualifications default to true.
        val unselectedJobQualification = "C++"
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, "Java")
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, "Qt")
        jobQualificationsRepo.readQualification(JobQualifications.LANGUAGES, unselectedJobQualification)

        jobQualificationsRepo.readInitialQualificationsSize(jobQualificationsRepo.getListQualifications().size).toString()
        val initialListOfJobQualificationsSize = jobQualificationsRepo.getListQualifications().size

        // Simulate unselecting 1 job qualification like a user would do in the app.
        val listOfJobQualificationDomainModels = jobQualificationsRepo.getListQualifications()
        val qual = listOfJobQualificationDomainModels.find {
            q: JobQualificationDomainModel -> q.qualification == unselectedJobQualification
        }
        qual?.selectForMatch(false)

        // Update the list.
        val idx = listOfJobQualificationDomainModels.indexOf(qual)
        if (qual != null) {
            listOfJobQualificationDomainModels[idx] = qual
        }

        // Get selected qualifications.
        var selectedJobQualificationsSize = 0
        listOfJobQualificationDomainModels.forEach {
            if (it.isSelectedForMatch) {
                selectedJobQualificationsSize++
            }
        }

        assertTrue {
            75 == ((selectedJobQualificationsSize.toDouble() / initialListOfJobQualificationsSize * 100).toInt())
        }
    }
}