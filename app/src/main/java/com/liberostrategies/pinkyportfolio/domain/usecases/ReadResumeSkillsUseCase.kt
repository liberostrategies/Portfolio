package com.liberostrategies.pinkyportfolio.domain.usecases

import com.liberostrategies.pinkyportfolio.data.repo.IResumeSkillsRepository

class ReadResumeSkillsUseCase(private val resumeSkillsRepo: IResumeSkillsRepository) {
    suspend operator fun invoke(companyIndex: Int, jobIndex: Int): UseCaseResult<String> {
        val result = resumeSkillsRepo.readSkills(companyIndex, jobIndex)
        return if (result.isEmpty()) {
            UseCaseResult.Warning("No results")
        } else {
            UseCaseResult.Success(result)
        }
    }
}