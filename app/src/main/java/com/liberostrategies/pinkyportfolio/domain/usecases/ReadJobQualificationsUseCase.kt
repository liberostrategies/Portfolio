package com.liberostrategies.pinkyportfolio.domain.usecases

import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel

class ReadJobQualificationsUseCase(private val jobQualificationsRepo: IJobQualificationRepository) {
    suspend operator fun invoke(category: String): UseCaseResult<List<JobQualificationDomainModel>> {
        val result = jobQualificationsRepo.readQualifications(category)
        val listJobQualifications = mutableListOf<JobQualificationDomainModel>()
        return if (result.isEmpty()) {
            UseCaseResult.Warning("No results")
        } else {
            result.forEach {
                listJobQualifications.add(
                    JobQualificationDomainModel(
                        category,
                        it.qualification
                    )
                )
            }
            UseCaseResult.Success(listJobQualifications)
        }
    }
}