package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

interface IJobQualificationRepository {
    suspend fun readQualification(
        category: String,
        jobQualification: String
    )
//    suspend fun readQualifications(
//        category: String
//    ): MutableList<JobQualificationDataModel>

    fun readQualificationsSize(size: Int)

    fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int

}