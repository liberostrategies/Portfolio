package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.source.IJobQualificationDataSource
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel

class JobQualificationsRepo(private val dataSource: IJobQualificationDataSource) : IJobQualificationRepository {
    override suspend fun readQualification(category: String, jobQualification: String) {
        dataSource.readQualification(category, jobQualification)
    }

    override fun getListQualifications(): MutableList<JobQualificationDomainModel> {
        val listOfJobQualificationDataModels = dataSource.getListQualifications()
        val listOfJobQualificationDomainModel = mutableListOf<JobQualificationDomainModel>()
        for (dataModel in listOfJobQualificationDataModels) {
            listOfJobQualificationDomainModel.add(JobQualificationDomainModel(dataModel.category, dataModel.qualification))
        }
        return listOfJobQualificationDomainModel
    }
    override fun readInitialQualificationsSize(size: Int) {
        return dataSource.readInitialQualificationsSize(size)
    }

    override fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int {
        return dataSource.matchQualificationsWithSkills(selectedJobQualificationsSize)
    }
}