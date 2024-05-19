package com.liberostrategies.pinkyportfolio.screens.videos

import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

const val videoUriTeamsPlay = "https://www.youtube.com/watch?v=xc8nAcVvpxY&pp=ygUPamV0cGFjayBjb21wb3Nl"
const val videoUriRealmDb = "https://www.youtube.com/watch?v=xKmEOXZsU_0&pp=ygUeZ29vZ2xlIGlvIGtvdGxpbiBtdWx0aXBsYXRmb3Jt"

@Composable
fun VideosScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                VideoListItem(title = "WristAway App KMP Demo", videoUri = videoUriTeamsPlay)
            }
            item {
                VideoListItem(title = "Architecture Components", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Realm DB", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: Lint Unit Test Coverage", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: MockK", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: Fake Google Play Billing API", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: Espresso", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: Use Cases using Truth", videoUri = videoUriRealmDb)
            }
        }
    }
}

@Composable
fun VideoListItem(title: String, videoUri: String) {
    var openVideo by remember { mutableStateOf(false) }
    var colorClicked by remember { mutableStateOf(Color.LightGray) }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = colorClicked,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .padding(1.dp)
            .clickable(true,
                onClick = {
                    openVideo = true
                }
            )
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            lineHeight = 400.sp,
            modifier = Modifier.padding(5.dp),
        )
    }

    if (openVideo) {
        WebView(
            videoUri,
            onDismissRequest = {
                openVideo = false
                colorClicked = Color(red = 0, green = 102, blue = 139)
            }
        )
    }
}

@Composable
fun WebView(
    videoUri: String,
    onDismissRequest: () -> Unit
){
    AndroidView(
        factory = {
            android.webkit.WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }},
        update = {
            it.loadUrl(videoUri)
            onDismissRequest()
        }
    )
}