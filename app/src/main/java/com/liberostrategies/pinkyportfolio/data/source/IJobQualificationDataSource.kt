package com.liberostrategies.pinkyportfolio.data.source

import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

/**
 * Each job qualification belongs to predefined categories. 
 * Categories are not editable by the user.
 * Job qualifications can by typed as input by the user.
 */
interface IJobQualificationDataSource {
    suspend fun createQualification(
        category: String, 
        qualification: String
    ) : JobQualificationDataModel

    suspend fun readQualifications(
        category: String
    ): MutableList<JobQualificationDataModel>

    suspend fun readAllQualifications(): MutableList<JobQualificationDataModel>

    suspend fun getListQualifications(): MutableList<JobQualificationDataModel>

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

class JobQualificationAlreadyExistsException(
    private val category: String,
    private val qualification: String /** Manually entered qualification. */
) : Exception() {
    override val message: String
        get() = "Job Qualification [$qualification] for Category [$category] already exists!"
}

class JobQualificationsDoNotExistException(
    private val category: String,
) : Exception() {
    override val message: String
        get() = "Job Qualifications do not exist for Category [$category]!"
}
