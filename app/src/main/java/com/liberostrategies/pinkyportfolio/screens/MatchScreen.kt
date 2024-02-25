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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.data.source.JobQualificationsDoNotExistException
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.screens.JobQualifications.Companion.MAP_JOB_QUALIFICATIONS

private class JobQualifications {
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

        //val MAP_JOB_QUALIFICATIONS = mutableMapOf<String, Pair<String, String>>()
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
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        JobQualificationsList()
        FilledTonalButton(
            modifier = Modifier
                .height(50.dp)
                .padding(top = 5.dp),
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Match Job Qualifications to Skills")
        }
    }
}

@Composable
fun ColumnScope.JobQualificationsList() {
    val db1 = Firebase.firestore
    val collectionJobQuals = db1.collection("jobqualifications")

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .weight(1f)
    ) {
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.LANGUAGES,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.LANGUAGES)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.IDES,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.IDES)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.DATABASES,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.DATABASES)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.TESTINGTOOLS,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.TESTINGTOOLS)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.VERSIONCONTROL,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.VERSIONCONTROL)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.WEBSERVER,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.WEBSERVER)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.FORMATTING,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.FORMATTING)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.GRAPHICS,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.GRAPHICS)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.PROCESSES,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.PROCESSES)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.PROJECTTOOLS,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.PROJECTTOOLS)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.DOCUMENTATION,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.DOCUMENTATION)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.OSES,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.OSES)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.REQUIREMENTS,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.REQUIREMENTS)
            )
        }
        item {
            Category(
                collectionJobQuals = collectionJobQuals,
                categoryKey = JobQualifications.CERTIFICATIONS,
                categoryDisplay = MAP_JOB_QUALIFICATIONS.getValue(JobQualifications.CERTIFICATIONS)
            )
        }
    }
}

@Composable
fun Category(
    collectionJobQuals: CollectionReference,
    categoryKey: String,
    categoryDisplay: String,
) {
    val docCertifications = collectionJobQuals.document(categoryKey)
    val listQualifications = mutableListOf<JobQualificationDomainModel>()
    var size by remember { mutableIntStateOf(0) }

    // NOTE: Could not figure out how to put this Firebase DB read into FirebaseDataSource.kt.
    // Kept losing items in listQualifications.
    docCertifications.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Logger.d("MatchScreen") { "$categoryKey qualifications: ${document.data}" }
                var i = 0
                while (document.data?.get("$i") != null) {
                    val q = document.data?.get("$i").toString()
                    listQualifications.add(JobQualificationDomainModel(categoryKey, q))
                    i++
                    size = i
                }
                Logger.d("MatchScreen") { "Qualifications 1[$listQualifications]" }
            } else {
                Logger.d("MatchScreen") { "No such document" }
            }
            Logger.d("MatchScreen") { "Qualifications 2 size=${listQualifications.size} [$listQualifications]" }
        }
        .addOnFailureListener { exception ->
            Logger.d("MatchScreen") { "get failed with $exception" }
            throw JobQualificationsDoNotExistException(exception.toString())
        }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Logger.d("MatchScreen") { "size $size ${listQualifications.size}" }
        Text(
            text = categoryDisplay,
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        listQualifications.forEach {
            val (checkedState, onStateChange) = remember { mutableStateOf(true) }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .toggleable(
                        value = checkedState,
                        onValueChange = { onStateChange(!checkedState) },
                        role = Role.Checkbox
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )

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
}
