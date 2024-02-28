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

    private val setJobQualifications = mutableSetOf<JobQualificationDomainModel>()

    fun addJobQualification(category: String, jobQualification: String) {
        setJobQualifications.add(JobQualificationDomainModel(category, jobQualification))
    }

    fun selectJobQualification(category: String, jobQualification: String) {
        val qual  = setJobQualifications.find {
                q: JobQualificationDomainModel -> q.qualification == jobQualification
        }
        qual?.selectForMatch(true)
        Logger.e(this.javaClass.simpleName) { "add $jobQualification, setJobQualifications $setJobQualifications." }
    }

    fun unselectJobQualification(jobQualification: String) {
        val qual  = setJobQualifications.find {
            q: JobQualificationDomainModel -> q.qualification == jobQualification
        }
        qual?.selectForMatch(false)

        Logger.e(this.javaClass.simpleName) { "remove $jobQualification, setJobQualifications $setJobQualifications." }
    }

    fun getJobQualifications() : MutableSet<JobQualificationDomainModel> {
        return setJobQualifications
    }

    private var initialQualificationsSize = 0
    fun setInitialQualificationsSize(size: Int) {
       initialQualificationsSize = size
        Logger.d(this.javaClass.simpleName) { "INITIAL size $size" }
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

    public fun getSelectedJobQualificationsSize() : Int {
        val listSelected = setJobQualifications.filter {
            q: JobQualificationDomainModel -> q.isSelectedForMatch
        }
        return listSelected.size
    }

    /**
     * Return percentage match of job qualifications with resume skills.
     * TODO: Move this business logic to a use case.
     */
    fun matchQualificationsWithSkills() : Int {
        Logger.e(this.javaClass.simpleName) { "initialQualificationsSize($initialQualificationsSize), getSelectedJobQualificationsSize(${getSelectedJobQualificationsSize()}) = $setJobQualifications" }
        return (getSelectedJobQualificationsSize().toDouble() / initialQualificationsSize * 100).toInt()
    }
}