package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel
import com.liberostrategies.pinkyportfolio.data.source.IJobQualificationDataSource

class JobQualificationsRepo(private val dataSource: IJobQualificationDataSource) : IJobQualificationRepository {
    override suspend fun createQualification(
        category: String,
        qualification: String
    ): JobQualificationDataModel {
        return dataSource.createQualification(category, qualification)
    }

    override suspend fun readQualification(category: String, jobQualification: String) {
        dataSource.readQualification(category, jobQualification)
    }

    override suspend fun readQualifications(category: String): MutableList<JobQualificationDataModel> {
        return dataSource.readQualifications(category)
    }

    override fun readQualificationsSize(size: Int) {
        return dataSource.readQualificationsSizeUseCase(size)
    }

    override fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int {
        return dataSource.matchQualificationsWithSkills(selectedJobQualificationsSize)
    }

    override suspend fun readAllQualifications(): MutableList<JobQualificationDataModel> {
        return dataSource.readAllQualifications()
    }

    override suspend fun updateQualification(
        category: String,
        qualification: String,
        newQualificationValue: String
    ) {
        dataSource.updateQualification(category, qualification, newQualificationValue)
    }

    override suspend fun deleteQualification(category: String, qualification: String) {
        dataSource.deleteQualification(category, qualification)
    }
}