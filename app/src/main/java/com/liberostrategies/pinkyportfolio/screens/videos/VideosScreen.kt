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
const val videoUrlEspressoTests = "https://youtu.be/YkjZ5Ttat2s"
const val videoUrlArchComponents = "https://youtu.be/Ks1U3FgSKRI"
@Composable
fun VideosScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item { VideosTypeHeader(typeText = "Android WristAway App Videos") }
            item {
                VideoListItem(title = "WristAway App Kotlin Promo", videoUri = videoUriTeamsPlay)
            }
            item {
                VideoListItem(title = "Architecture Components", videoUri = videoUrlArchComponents)
            }
            item {
                VideoListItem(title = "Realm DB", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit: ScoreModes", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit: DataSource", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit: DataModel", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit: RealmDbDataModel", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit: Repo", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: JUnit: DomainModel", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: ViewModels", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Tests: Espresso", videoUri = videoUrlEspressoTests)
            }
            item {
                VideoListItem(title = "WristAway App Compose KMP", videoUri = videoUriTeamsPlay)
            }

            item { VideosTypeHeader(typeText = "QT/QML UI Views") }
            item {
                VideoListItem(title = "DeNovix Products", videoUri = "https://youtu.be/C4cQgTu-7wc")
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

@Composable
fun VideosTypeHeader(
    typeText: String,
) {
    Text(
        text = "$typeText:",
        color = Color.LightGray,
        fontSize = 16.sp,
        lineHeight = 400.sp,
        modifier = Modifier.padding(5.dp)
    )
}