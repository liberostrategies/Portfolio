package com.liberostrategies.pinkyportfolio.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun PdfResumeScreen() {
    val resumeUrl = "https://github.com/liberostrategies/Portfolio/blob/dev/Docs/PinkyRamos_Resume.pdf"
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(resumeUrl)) }
    context.startActivity(intent)
}