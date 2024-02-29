package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

interface IJobQualificationRepository {
    suspend fun createQualification(
        category: String,
        qualification: String
    ) : JobQualificationDataModel

    suspend fun readQualification(
        category: String,
        jobQualification: String
    )
    suspend fun readQualifications(
        category: String
    ): MutableList<JobQualificationDataModel>

    fun readQualificationsSize(size: Int)

    fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int

    suspend fun readAllQualifications(): MutableList<JobQualificationDataModel>

    suspend fun updateQualification(
        category: String,
        qualification: String,
        newQualificationValue: String
    )

    suspend fun deleteQualification(
        category: String,
        qualification: String
    )
}