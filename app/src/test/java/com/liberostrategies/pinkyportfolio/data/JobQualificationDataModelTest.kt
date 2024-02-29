package com.liberostrategies.pinkyportfolio.data

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel
import com.liberostrategies.pinkyportfolio.screens.JobQualifications
import kotlin.test.Test
import kotlin.test.assertEquals

class JobQualificationDataModelTest {

    @Test
    fun `Create - Valid values`() {
        val validQualification = "Kotlin"
        val jobQualification = JobQualificationDataModel(JobQualifications.LANGUAGES, validQualification)
        assertEquals(jobQualification.category, JobQualifications.LANGUAGES)
        assertEquals(jobQualification.qualification, validQualification)
    }
}