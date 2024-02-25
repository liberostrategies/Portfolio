package com.liberostrategies.pinkyportfolio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.ui.viewmodels.JobQualificationsViewModel

private class JobQualifications() {
    companion object {
        const val CERTIFICATIONS = "certifications"
        const val DATABASES = "databases"
        const val DOCUMENTATION = "documentation"
        const val FORMATTING = "formatting"
        const val GRAPHICS = "graphics"
        const val IDES = "ides"
        const val LANGUAGES = "languages"
        const val OSES = "oses"
        const val PROCESSES = "processes"
        const val PROJECTTOOLS = "projecttools"
        const val REQUIREMENTS = "requirements"
        const val TESTINGTOOLS = "testingtools"
        const val VERSIONCONTROL = "versioncontrol"
        const val WEBSERVER = "webserver"
    }
}
@Composable
fun MatchScreen(
    repoJobQual: IJobQualificationRepository,
) {


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Category(
                category = JobQualifications.CERTIFICATIONS,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.CERTIFICATIONS),
            )
        }
        item {
            Category(
                category = JobQualifications.DATABASES,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.DATABASES),
            )
        }
        item {
            Category(
                category = JobQualifications.DOCUMENTATION,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.DOCUMENTATION),
            )
        }
        item {
            Category(
                category = JobQualifications.FORMATTING,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.FORMATTING),
            )
        }
        item {
            Category(
                category = JobQualifications.GRAPHICS,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.GRAPHICS),
            )
        }
        item {
            Category(
                category = JobQualifications.IDES,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.IDES),
            )
        }
        item {
            Category(
                category = JobQualifications.LANGUAGES,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.LANGUAGES),
            )
        }
        item {
            Category(
                category = JobQualifications.OSES,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.OSES),
            )
        }
        item {
            Category(
                category = JobQualifications.PROCESSES,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.PROCESSES),
            )
        }
        item {
            Category(
                category = JobQualifications.PROJECTTOOLS,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.PROJECTTOOLS),
            )
        }
        item {
            Category(
                category = JobQualifications.REQUIREMENTS,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.REQUIREMENTS),
            )
        }
        item {
            Category(
                category = JobQualifications.TESTINGTOOLS,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.TESTINGTOOLS),
            )
        }
        item {
            Category(
                category = JobQualifications.VERSIONCONTROL,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.VERSIONCONTROL),
            )
        }
        item {
            Category(
                category = JobQualifications.WEBSERVER,
                jobQualifications = JobQualificationsViewModel(repoJobQual).readJobQualifications(JobQualifications.WEBSERVER),
            )
        }
    }
}

@Composable
fun Category(
    category: String,
    jobQualifications: List<JobQualificationDomainModel>
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = category,
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        jobQualifications.forEach {
            Text(
                text = it.qualification,
                modifier = Modifier
                    .padding(start = 10.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
