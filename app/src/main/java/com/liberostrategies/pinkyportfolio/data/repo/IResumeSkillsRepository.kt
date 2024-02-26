package com.liberostrategies.pinkyportfolio.data.repo

interface IResumeSkillsRepository {
    fun readSkills(
        companyIndex: Int,
        jobIndex: Int,      // NOTE: Job may not have "tech" skill field.
    ): String
}