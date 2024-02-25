package com.liberostrategies.pinkyportfolio.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.data.repo.JobQualificationsRepo
import com.liberostrategies.pinkyportfolio.data.source.FirebaseDataSource
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.domain.usecases.JobQualificationsUseCases
import com.liberostrategies.pinkyportfolio.domain.usecases.ReadJobQualificationsUseCase
import com.liberostrategies.pinkyportfolio.domain.usecases.UseCaseResult
import kotlinx.coroutines.launch

class JobQualificationsViewModel(
    private val repoJobQualifications: IJobQualificationRepository = JobQualificationsRepo(FirebaseDataSource()),
    private val jobQualificationsUseCases: JobQualificationsUseCases = JobQualificationsUseCases(
        ReadJobQualificationsUseCase(repoJobQualifications)
    )
) : ViewModel() {

    fun readJobQualifications(category: String): MutableList<JobQualificationDomainModel> {
        val listJobQualifications = mutableListOf<JobQualificationDomainModel>()
        viewModelScope.launch {

            when (val result = jobQualificationsUseCases.readJobQualifications(category)) {
                is UseCaseResult.Warning -> {
                    Logger.e("JobQualificationsViewModel") { "No job qualifications result for $category." }
                }
                is UseCaseResult.Success -> {
                    result.data?.forEach {
                        Logger.d("JobQualificationsViewModel") { ">>>>${it.category} ${it.qualification}" }
                        listJobQualifications.add(JobQualificationDomainModel(it.category, it.qualification))
                    }
                }
                is UseCaseResult.Error -> {
                    Logger.e("JobQualificationsViewModel") { "Error read job qualifications result for $category." }
                }
                else -> {
                    Logger.e("JobQualificationsViewModel") { "Unhandled read all job qualifications result for $category." }
                }
            }
        }
        return listJobQualifications
    }
}