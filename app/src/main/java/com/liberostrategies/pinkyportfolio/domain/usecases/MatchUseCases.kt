package com.liberostrategies.pinkyportfolio.domain.usecases

data class MatchUseCases(
    val readJobQualificationUseCase: ReadJobQualificationUseCase,
//    val readJobQualifications: ReadJobQualificationsUseCase,
    val readJobQualificationsSize: ReadJobQualificationsSizeUseCase,
    val matchQualificationsWithSkillsUseCase: MatchQualificationsWithSkillsUseCase,
    val readResumeSkillsUseCase: ReadResumeSkillsUseCase,
)
