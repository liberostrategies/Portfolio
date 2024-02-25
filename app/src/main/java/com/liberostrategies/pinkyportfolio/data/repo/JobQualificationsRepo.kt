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

    override suspend fun readQualifications(category: String): MutableList<JobQualificationDataModel> {
        dataSource.readQualifications(category)
        return dataSource.getListQualifications()
    }

    override suspend fun readAllQualifications(): MutableList<JobQualificationDataModel> {
        return dataSource.readAllQualifications()
    }

    override suspend fun readAllQualificationsAsJson(): String {
        return dataSource.readAllQualificationsAsJson()
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