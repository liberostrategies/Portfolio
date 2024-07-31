package com.liberostrategies.pinkyportfolio.screens

import android.content.Context
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.domain.download.AndroidDownloader

@Composable
fun ResumeScreen(
    context: Context
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

    var performanceHighlightCount by remember { mutableStateOf(0) }
    val docPerformanceHighlights = resumeDoc.document("performanceHighlights")
    docPerformanceHighlights.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                performanceHighlightCount = document.data?.get("performanceHighlightCount").toString().toInt()
            }
        }

    var certificationCount by remember { mutableStateOf(0) }
    val docCertificationsAndEducation = resumeDoc.document("certificationsAndEducation")
    docCertificationsAndEducation.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                certificationCount = document.data?.get("certificationCount").toString().toInt()
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

            item { Objective(resumeDoc) }

            item { SectionHeader(headerText = "Skills & Technology")}
            item { SkillsAndTech(resumeDoc.document("skillsAndTechnology")) }

            item { SectionHeader(headerText = "Performance Highlights")}
            for (i in 0..<performanceHighlightCount) {
                item { PerformanceHighlight(i, resumeDoc.document("performanceHighlights")) }
            }

            item { SectionHeader(headerText = "Experience")}
            for (i in (companyCount - 1) downTo 0) {
                item { Company(i, docCompanies) }
            }

            item { SectionHeader(headerText = "Certifications & Education")}
            for (i in (certificationCount - 1) downTo 0) {
                item { Certification(i, resumeDoc.document("certificationsAndEducation")) }
            }
            item { College(resumeDoc.document("certificationsAndEducation"))}
        }
    }
}

@Composable
fun SectionHeader(headerText: String) {
    val colorBackground by remember { mutableStateOf(Color.LightGray) }
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = colorBackground,
        ),
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .padding(1.dp)
    ) {
        Text(
            text = headerText.uppercase(),
            color = Color.Black,
            fontSize = 16.sp,
            lineHeight = 400.sp,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
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


    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                )
            ){
                append(whoami)
            }
            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            ) {
                append(description)
            }
            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                )
            ) {
                append("\n${github}")
            }
        },
    )
}

@Composable
fun Company(
    idxCompany: Int,
    docCompanies: DocumentReference
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
                Job(i, docCompany)
            }
        }
    }
}

@Composable
fun Job(
    idxJob: Int,
    docCompany: CollectionReference,
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

private fun splitPair(namedPair: String) : Array<String> {
    return namedPair.split(":")
        .toTypedArray()
}

@Composable
fun SkillsAndTech(
    docSkillsAndTech: DocumentReference
) {
    var skillsAndTech by remember { mutableStateOf("") }
    docSkillsAndTech.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                skillsAndTech = document.data?.get("skillsAndTechList").toString()
            }
        }
    Text(
        text = skillsAndTech,
        modifier = Modifier,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun PerformanceHighlight(
    idxHighlight: Int,
    docPerformanceHighlight: DocumentReference
) {
    var performanceHighlight by remember { mutableStateOf("") }
    var performance by remember { mutableStateOf("") }
    var highlight by remember { mutableStateOf("") }

    docPerformanceHighlight.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                performanceHighlight = document.data?.get("perf$idxHighlight").toString()
                performance = splitPair(performanceHighlight)[0].trim()
                highlight = splitPair(performanceHighlight)[1].trim()
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
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                            )
                        ) {
                            append("${Typography.bullet} $performance: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                            )
                        ) {
                            append(highlight)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Certification(
    idxCert: Int,
    docCertification: DocumentReference
) {
    var certificationYear by remember { mutableStateOf("") }
    var certification by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    docCertification.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                certificationYear = document.data?.get("cert$idxCert").toString()
                certification = splitPair(certificationYear)[0].trim()
                year = splitPair(certificationYear)[1].trim()
            }
        }

    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = certification,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = year,
                modifier = Modifier
                    .align(Alignment.Bottom),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun College(
    docCertification: DocumentReference
) {
    val docCollege = docCertification.collection("college")
    val docDrexel =  docCollege.document("Drexel")
    var universityName by remember { mutableStateOf("") }
    var concentration by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var graduationYear by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var scholarships by remember { mutableStateOf("") }
    docDrexel.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                universityName = document.data?.get("universityName").toString()
                concentration = document.data?.get("concentration").toString()
                degree = document.data?.get("degree").toString()
                graduationYear = document.data?.get("graduationYear").toString()
                location = document.data?.get("location").toString()
                scholarships = document.data?.get("scholarships").toString()
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
                text = universityName,
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

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = degree,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = graduationYear,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .align(Alignment.Bottom),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(

                        )
                    ) {
                        append(concentration)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("Scholarships: $scholarships")
                    }
                }
            )
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