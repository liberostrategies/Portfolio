package com.liberostrategies.pinkyportfolio.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.data.repo.IResumeSkillsRepository
import com.liberostrategies.pinkyportfolio.data.repo.JobQualificationsRepo
import com.liberostrategies.pinkyportfolio.data.repo.ResumeSkillsRepo
import com.liberostrategies.pinkyportfolio.data.source.FirebaseJobQualificationDataSource
import com.liberostrategies.pinkyportfolio.data.source.FirebaseResumeSkillDataSource
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.domain.usecases.MatchUseCases
import com.liberostrategies.pinkyportfolio.domain.usecases.ReadJobQualificationsUseCase
import com.liberostrategies.pinkyportfolio.domain.usecases.ReadResumeSkillsUseCase
import com.liberostrategies.pinkyportfolio.domain.usecases.UseCaseResult
import kotlinx.coroutines.launch

class MatchViewModel(
    private val repoJobQualifications: IJobQualificationRepository = JobQualificationsRepo(FirebaseJobQualificationDataSource()),
    private val repoResumeSkills: IResumeSkillsRepository = ResumeSkillsRepo(FirebaseResumeSkillDataSource()),
    private val matchUseCases: MatchUseCases = MatchUseCases(
        ReadJobQualificationsUseCase(repoJobQualifications),
        ReadResumeSkillsUseCase(repoResumeSkills)
    )
) : ViewModel() {

    private val setJobQualifications = mutableSetOf<String>()

    fun addJobQualification(techSkill: String) {
        setJobQualifications.add(techSkill)
    }

    fun getJobQualifications() : MutableSet<String> {
        return setJobQualifications
    }

    private val setResumeSkills = mutableSetOf<String>()

    fun addResumeSkill(techSkill: String) {
        setResumeSkills.add(techSkill)
    }

    fun getTechSkills() : MutableSet<String> {
        return setResumeSkills
    }

    private val skills = StringBuilder()

    fun getSkills() : String {
        return skills.removeSuffix(".").toString()
    }

    fun clearSkills() : String {
        return skills.clear().toString()
    }
    fun readResumeSkills(companyIndex: Int, jobIndex: Int): String {

        viewModelScope.launch {
            when (val result = matchUseCases.readResumeSkillsUseCase(companyIndex, jobIndex)) {
                is UseCaseResult.Warning -> {
                    Logger.e("JobQualificationsViewModel") { "No tech skills for company$companyIndex, job$jobIndex." }
                }
                is UseCaseResult.Success -> {
                    Logger.d(this.javaClass.simpleName) { ">>>>${result.data}" }
                    skills.clear().append(result.data)
                }
                is UseCaseResult.Error -> {
                    Logger.e(this.javaClass.simpleName) { "Error read job qualifications result for  company$companyIndex, job$jobIndex." }
                }
            }
        }
        return skills.toString()
    }
    fun readJobQualifications(category: String): MutableList<JobQualificationDomainModel> {
        val listJobQualifications = mutableListOf<JobQualificationDomainModel>()
        viewModelScope.launch {
            when (val result = matchUseCases.readJobQualifications(category)) {
                is UseCaseResult.Warning -> {
                    Logger.e(this.javaClass.simpleName) { "No job qualifications result for $category." }
                }
                is UseCaseResult.Success -> {
                    result.data?.forEach {
                        Logger.d(this.javaClass.simpleName) { ">>>>${it.category} ${it.qualification}" }
                        listJobQualifications.add(JobQualificationDomainModel(it.category, it.qualification))
                    }
                }
                is UseCaseResult.Error -> {
                    Logger.e(this.javaClass.simpleName) { "Error read job qualifications result for $category." }
                }
            }
        }
        return listJobQualifications
    }
}