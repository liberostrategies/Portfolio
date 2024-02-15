package com.liberostrategies.pinkyportfolio.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.domain.AndroidDownloader

@Composable
fun PdfResumeScreen(context: Context) {
    val resumeUrl = "https://liberostrategies.com/downloads/PinkyRamos_Resume.pdf"
    val resumePage1Url = "https://liberostrategies.com/downloads/PinkyRamos_ResumeP1.png"
    val resumePage2Url = "https://liberostrategies.com/downloads/PinkyRamos_ResumeP2.png"

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            AsyncImage(
                model = resumePage1Url,
                contentDescription = "Resume Page 1"
            )
        }
        item {
            AsyncImage(
                model = resumePage2Url,
                contentDescription = "Resume Page 2"
            )
        }
        item {
            Objective()
        }
        item {
            Button(
                onClick = {
                    val downloader = AndroidDownloader(context)
                    downloader.downloadFile(resumeUrl)
                }
            ) {
                Text("Download PDF")
            }
        }
    }
}

@Composable
fun Objective() {
    val db = Firebase.firestore
    val resumeDoc = db.collection("resume")
    val docSummary = resumeDoc.document("summary")
    var whoami by remember { mutableStateOf("TBD") }
    var description by remember { mutableStateOf("TBD") }
    var github by remember { mutableStateOf("TBD") }
    docSummary.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Logger.d("ResumeTypeScreen") { "DocumentSnapshot data: ${document.data}" }
                whoami = document.data?.get("whoami").toString()
                description = document.data?.get("description").toString()
                github = document.data?.get("github").toString()
            } else {
                Logger.d("ResumeTypeScreen") { "No such document" }
            }
        }
        .addOnFailureListener { exception ->
            Logger.d("ResumeTypeScreen") { "get failed with ${exception}" }
        }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = whoami,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Text (
            text = description,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Start,
        )
        Text(
            text = github,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Company() {

}