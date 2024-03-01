package com.liberostrategies.pinkyportfolio.model

import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.screens.JobQualifications
import kotlin.test.Test
import kotlin.test.assertEquals

class JobQualificationDomainModelTest {
    @Test
    fun `Create - Valid values`() {
        val validQualification = "Kotlin"
        val jobQualification = JobQualificationDomainModel(JobQualifications.LANGUAGES, validQualification)
        assertEquals(jobQualification.category, JobQualifications.LANGUAGES)
        assertEquals(jobQualification.qualification, validQualification)
    }
}