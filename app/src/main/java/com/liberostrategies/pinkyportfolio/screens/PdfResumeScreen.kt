package com.liberostrategies.pinkyportfolio.screens

import android.content.Context
import androidx.compose.runtime.Composable
import com.liberostrategies.pinkyportfolio.domain.AndroidDownloader

@Composable
fun PdfResumeScreen(context: Context) {
    val resumeUrl = "https://liberostrategies.com/downloads/PinkyRamos_Resume.pdf"
    val downloader = AndroidDownloader(context)
    downloader.downloadFile(resumeUrl)
}