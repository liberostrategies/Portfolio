package com.liberostrategies.pinkyportfolio.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.domain.download.AndroidDownloader
import com.liberostrategies.pinkyportfolio.ui.viewmodels.MatchViewModel

@Composable
fun ResumeScreen(
    context: Context,
    matchViewModel: MatchViewModel
) {
    val db = Firebase.firestore
    val resumeDoc = db.collection("resume")
    var companyCount by remember { mutableIntStateOf(0) }
    val docCompanies = resumeDoc.document("companies")
    docCompanies.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                companyCount = document.data?.get("companycount").toString().toInt()
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DownloadResume(context)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                Objective(resumeDoc)
            }

            for (i in (companyCount - 1) downTo 0) {
                item {
                    Company(i, docCompanies, matchViewModel)
                }
            }
        }
    }
}

@Composable
fun DownloadResume(context: Context) {
    val resumeUrl = "https://liberostrategies.com/downloads/PinkyRamos_Resume.pdf"

    Button(
        onClick = {
            val downloader = AndroidDownloader(context)
            downloader.downloadFile(resumeUrl)
        },
        shape = RoundedCornerShape(5.dp),
    ) {
        Text("Download Resume PDF")
    }
}

@Composable
fun Objective(resumeDoc: CollectionReference) {
    val docSummary = resumeDoc.document("summary")
    var whoami by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var github by remember { mutableStateOf("") }
    docSummary.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                whoami = document.data?.get("whoami").toString()
                description = document.data?.get("description").toString()
                github = document.data?.get("github").toString()
            } else {
                Logger.d("ResumeScreen") { "No such document" }
            }
        }
        .addOnFailureListener { exception ->
            Logger.d("ResumeScreen") { "get failed with ${exception}" }
        }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = whoami,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text (
            text = description,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = github,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Company(
    idxCompany: Int,
    docCompanies: DocumentReference,
    matchViewModel: MatchViewModel
) {
    val docCompany = docCompanies.collection("company${idxCompany}")
    val docCompanyInfo = docCompany.document("info${idxCompany}")
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var jobCount by remember { mutableIntStateOf(0) }
    docCompanyInfo.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                name = document.data?.get("name").toString()
                location = document.data?.get("location").toString()
                jobCount = document.data?.get("jobcount").toString().toInt()
            }
        }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = name,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .weight(1f),
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    text = location,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            for (i in (jobCount-1) downTo 0) {
                Job(i, docCompany, matchViewModel)
            }
        }
    }
}

@Composable
fun Job(
    idxJob: Int,
    docCompany: CollectionReference,
    matchViewModel: MatchViewModel
) {
    val docJob = docCompany.document("job${idxJob}")
    var title by remember { mutableStateOf("") }
    var startdate by remember { mutableStateOf("") }
    var enddate by remember { mutableStateOf("") }
    var duties by remember { mutableStateOf("") }
    var tech by remember { mutableStateOf("") }
    var notablesCount by remember { mutableIntStateOf(0) }
    docJob.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                title = document.data?.get("title").toString()
                startdate = document.data?.get("startdate").toString()
                enddate = document.data?.get("enddate").toString()
                duties = document.data?.get("duties").toString()
                tech = document.data?.get("tech").toString()
                matchViewModel.addResumeSkills(tech)
                notablesCount = if (document.data?.get("notablescount") == null) 0 else document.data?.get("notablescount").toString().toInt()
            }
        }


    Column (
        modifier = Modifier
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$startdate - $enddate",
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .align(Alignment.Bottom),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(
            text = duties,
            modifier = Modifier,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = tech,
            modifier = Modifier
                .padding(bottom = 5.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )

        val docNotables = docJob.collection("notables")
        for (i in (notablesCount-1) downTo 0) {
            Notable(i, docNotables)
        }
    }
}

@Composable
fun Notable(
    idxNotable: Int,
    docNotables: CollectionReference
) {
    val docNotable = docNotables.document("notable${idxNotable}")

    var note by remember { mutableStateOf("") }

    docNotable.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                note = document.data?.get("note").toString()
            }
        }

    Text(
        text = "${Typography.bullet} $note",
        modifier = Modifier,
        style = MaterialTheme.typography.bodyMedium
    )
}