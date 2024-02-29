package com.liberostrategies.pinkyportfolio.domain.usecases

import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository

class ReadJobQualificationUseCase(private val jobQualificationsRepo: IJobQualificationRepository) {
    suspend operator fun invoke(category: String, jobQualification: String): UseCaseResult<String> {
        jobQualificationsRepo.readQualification(category, jobQualification)
        // TODO: Consider any error conditions. Perhaps check for uniqueness.
        return (UseCaseResult.Success())
    }
}