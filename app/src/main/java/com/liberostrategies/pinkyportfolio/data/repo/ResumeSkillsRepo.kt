package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.data.source.IResumeSkillDataSource

class ResumeSkillsRepo(private val dataSource: IResumeSkillDataSource) : IResumeSkillsRepository {
    override suspend fun readSkills(companyIndex: Int, jobIndex: Int): String {
        dataSource.readSkills(companyIndex, jobIndex)
        return dataSource.getSkills()
    }
}