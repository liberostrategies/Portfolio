package com.liberostrategies.pinkyportfolio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.liberostrategies.pinkyportfolio.screens.JobQualifications.Companion.MAP_JOB_QUALIFICATIONS
import com.liberostrategies.pinkyportfolio.ui.viewmodels.MatchViewModel

open class JobQualifications {
    companion object {
        // Match Firebase keys
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

        // Map Firebase keys to Job Qualifications Categories for display.
        val MAP_JOB_QUALIFICATIONS = mutableMapOf(
            CERTIFICATIONS to "Certification",
            DATABASES to "Database",
            DOCUMENTATION to "Documentation",
            FORMATTING to "Formatting",
            GRAPHICS to "Graphics",
            IDES to "IDE",
            LANGUAGES to "Language",
            OSES to "OS",
            PROCESSES to "Process",
            PROJECTTOOLS to "Project Tools",
            REQUIREMENTS to "Requirements",
            TESTINGTOOLS to "Testing Tools",
            VERSIONCONTROL to "Version Control",
            WEBSERVER to "Webserver",
        )
    }
}

@Composable
fun MatchScreen(
    matchViewModel: MatchViewModel,
) {
    var skills by remember { mutableStateOf("") }
    val matchInstructions = "Match Job Qualifications to Resume Skills"
    var matchButtonText by remember { mutableStateOf(matchInstructions) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button( // TODO: Make this a common button, because it is reused.
            modifier = Modifier
                .height(50.dp)
                .padding(bottom = 5.dp),
            onClick = {
                skills = ""
                matchButtonText = matchViewModel.matchQualificationsWithSkills().toString() + "% Match"
            },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(matchButtonText)
        }

        JobQualificationsList(matchViewModel)
    }
}

@Composable
fun ColumnScope.JobQualificationsList(
    matchViewModel: MatchViewModel,
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .weight(1f)
    ) {
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.LANGUAGES)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.IDES)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.DATABASES)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.TESTINGTOOLS)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.VERSIONCONTROL)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.WEBSERVER)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.FORMATTING)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.GRAPHICS)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.PROCESSES)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.PROJECTTOOLS)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.DOCUMENTATION)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.OSES)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.REQUIREMENTS)
            )
        }
        item {
            Category(
                matchViewModel = matchViewModel,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.CERTIFICATIONS)
            )
        }
    }
}

@Composable
fun Category(
    matchViewModel: MatchViewModel,
    categoryDisplay: String,
) {
    val listQualifications = matchViewModel.getJobQualifications()

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = categoryDisplay,
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        listQualifications.forEach {

            if (MAP_JOB_QUALIFICATIONS.getValue(it.category) == categoryDisplay) {
                var checkedState by remember { mutableStateOf(true) }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = checkedState,
                            onValueChange = { checkedState = !checkedState },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    it.category
                    val qualification = it.qualification
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = {
                            checkedState = it
                            if (!it) {
                                matchViewModel.unselectJobQualification(qualification)
                                Logger.d("MatchScreen") { "Unselect $qualification. Total(${matchViewModel.getSelectedJobQualificationsSize()})" }
                            } else {
                                matchViewModel.selectJobQualification(qualification)
                                Logger.d("MatchScreen") { "Select $qualification. Total(${matchViewModel.getSelectedJobQualificationsSize()})" }
                            }
                        }
                    )

                    Text(
                        text = qualification,
                        modifier = Modifier
                            .padding(start = 10.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}