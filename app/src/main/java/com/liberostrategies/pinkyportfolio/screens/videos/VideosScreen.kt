package com.liberostrategies.pinkyportfolio.screens.videos

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun VideosScreen(context: Context) {
    val videoUriUnitTests = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        item { VideoPlayer(videoUriUnitTests) }
        item { VideoPlayer(videoUriUnitTests) }
    }


//    Box(modifier = Modifier
//            .fillMaxSize()) {
//        VideoPlayer(videoUriUnitTests)
//    }
}