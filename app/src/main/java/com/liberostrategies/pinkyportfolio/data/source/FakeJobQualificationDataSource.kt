package com.liberostrategies.pinkyportfolio.data.source

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

class FakeJobQualificationDataSource : IJobQualificationDataSource {
    private var mListQualifications = mutableListOf<JobQualificationDataModel>()
    private var initialQualificationsSize = 0

    override suspend fun readQualification(category: String, jobQualification: String) {
        mListQualifications.add(JobQualificationDataModel(category, jobQualification))
    }

    override fun readInitialQualificationsSize(size: Int) {
        initialQualificationsSize = size
    }

    override fun getListQualifications(): MutableList<JobQualificationDataModel> {
        return mListQualifications
    }

    override fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int {
        return (selectedJobQualificationsSize.toDouble() / initialQualificationsSize * 100).toInt()
    }
}