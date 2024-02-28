package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

interface IJobQualificationRepository {
    suspend fun createQualification(
        category: String,
        qualification: String
    ) : JobQualificationDataModel

    suspend fun readQualifications(
        category: String
    ): MutableList<JobQualificationDataModel>

//    suspend fun getListQualifications(): MutableList<JobQualificationDataModel>
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