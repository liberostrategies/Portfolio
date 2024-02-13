package com.liberostrategies.pinkyportfolio.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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