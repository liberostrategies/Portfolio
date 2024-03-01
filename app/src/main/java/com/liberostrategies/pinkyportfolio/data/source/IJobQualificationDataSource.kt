package com.liberostrategies.pinkyportfolio.data.source

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

/**
 * Each job qualification belongs to predefined categories. 
 * Categories are not editable by the user.
 * Job qualifications can by typed as input by the user.
 */
interface IJobQualificationDataSource {

    suspend fun readQualification(
        category: String,
        jobQualification: String
    )

    fun readInitialQualificationsSize(size: Int)

    suspend fun getListQualifications(): MutableList<JobQualificationDataModel>

    fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int
}

class JobQualificationsDoNotExistException(
    private val category: String,
) : Exception() {
    override val message: String
        get() = "Job Qualifications do not exist for Category [$category]!"
}
