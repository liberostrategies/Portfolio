package com.liberostrategies.pinkyportfolio.data

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel
import com.liberostrategies.pinkyportfolio.data.source.FakeJobQualificationDataSource
import com.liberostrategies.pinkyportfolio.screens.JobQualifications
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FakeJobQualificationDataSourceTest {
    private val dataSource = FakeJobQualificationDataSource()

    private val validLanguageQualification = "Kotlin"
    @Test
    fun `Read - Category of qualification`() = runBlocking {
        dataSource.getListQualifications().clear()
        dataSource.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        assertEquals(JobQualifications.LANGUAGES, dataSource.getListQualifications()[0].category)
    }

    @Test
    fun `Read - Qualification for category`() = runBlocking {
        dataSource.getListQualifications().clear()
        dataSource.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        assertEquals(validLanguageQualification, dataSource.getListQualifications()[0].qualification )
    }

    @Test
    fun `Read - Qualification list size`() = runBlocking {
        dataSource.getListQualifications().clear()
        // Initialize with 2 job qualifications.
        dataSource.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        dataSource.readQualification(JobQualifications.LANGUAGES, "Java")

        dataSource.readInitialQualificationsSize(dataSource.getListQualifications().size).toString()
        assertTrue { 2 == dataSource.getListQualifications().size }
    }

    @Test
    fun `Calculate percentage of matching selected job qualifications with resume skills`() = runBlocking{
        dataSource.getListQualifications().clear()
        // Job qualifications default to true.
        val unselectedJobQualification = "C++"
        dataSource.readQualification(JobQualifications.LANGUAGES, validLanguageQualification)
        dataSource.readQualification(JobQualifications.LANGUAGES, "Java")
        dataSource.readQualification(JobQualifications.LANGUAGES, "Qt")
        dataSource.readQualification(JobQualifications.LANGUAGES, unselectedJobQualification)

        dataSource.readInitialQualificationsSize(dataSource.getListQualifications().size).toString()
        val initialListOfJobQualificationsSize = dataSource.getListQualifications().size

        // Simulate unselecting 1 job qualification like a user would do in the app
        // by removing the data source in the list.
        dataSource.getListQualifications().removeIf {
            q: JobQualificationDataModel -> q.qualification == unselectedJobQualification
        }
        val selectedJobQualificationsSize = dataSource.getListQualifications().size

        assertTrue {
            75 == (selectedJobQualificationsSize.toDouble() / initialListOfJobQualificationsSize * 100).toInt()
        }
    }
}