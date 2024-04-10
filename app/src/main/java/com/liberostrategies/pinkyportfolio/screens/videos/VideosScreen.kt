package com.liberostrategies.pinkyportfolio.screens.videos

import android.content.Context
import androidx.compose.runtime.Composable

@Composable
fun VideosScreen(context: Context) {
    val videoUriUnitTests = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    VideoPlayer(videoUriUnitTests)

}