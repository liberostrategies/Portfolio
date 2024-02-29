package com.liberostrategies.pinkyportfolio.domain.usecases

import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository

class ReadJobQualificationsSizeUseCase(private val jobQualificationsRepo: IJobQualificationRepository) {
    operator fun invoke(size: Int): UseCaseResult<String> {
        jobQualificationsRepo.readQualificationsSize(size)
        // TODO: Consider any error conditions. Perhaps, if read were unsuccessful from Firebase DB.
        return (UseCaseResult.Success())
    }
}