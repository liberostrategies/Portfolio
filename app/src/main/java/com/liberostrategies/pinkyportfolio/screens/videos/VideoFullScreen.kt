package com.liberostrategies.pinkyportfolio.screens.videos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VideoFullScreen(
    uri: String = "",
    onClose: () -> Unit
) {
    VideoPlayer(uri, onClose)
}