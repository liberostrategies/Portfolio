package com.liberostrategies.pinkyportfolio.domain.usecases

import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel

class MatchQualificationsWithSkillsUseCase(private val jobQualificationsRepo: IJobQualificationRepository) {
    operator fun invoke(selectedJobQualificationsSize: Int): UseCaseResult<Int> {
        val result =
            jobQualificationsRepo.matchQualificationsWithSkills(selectedJobQualificationsSize)
        // TODO: Consider any error conditions. Perhaps check for uniqueness.
        return (UseCaseResult.Success(result))
    }
}