package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel

interface IJobQualificationRepository {
    suspend fun readQualification(
        category: String,
        jobQualification: String
    )

    fun getListQualifications(): MutableList<JobQualificationDomainModel>

    fun readInitialQualificationsSize(size: Int)

    fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int

}