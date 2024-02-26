package com.liberostrategies.pinkyportfolio.data.source

interface IResumeSkillDataSource {
    fun readSkills(
        companyIndex: Int,
        jobIndex: Int,      // NOTE: Job may not have "tech" skill field.
    ): String               // For now comma-delimited string as stored in Firebase DB.

    fun getSkills() : String
}