package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.source.IJobQualificationDataSource

class JobQualificationsRepo(private val dataSource: IJobQualificationDataSource) : IJobQualificationRepository {
    override suspend fun readQualification(category: String, jobQualification: String) {
        dataSource.readQualification(category, jobQualification)
    }

    override fun readQualificationsSize(size: Int) {
        return dataSource.readInitialQualificationsSize(size)
    }

    override fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int {
        return dataSource.matchQualificationsWithSkills(selectedJobQualificationsSize)
    }
}