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

            item { VideosTypeHeader(typeText = "Android WristAway App") }
            item {
                VideoListItem(title = "WristAway App: Kotlin Promo", videoUri = "https://youtu.be/KmCuZ-RtRYE")
            }
            item {
                VideoListItem(title = "WristAway App: Compose KMP (WIP)", videoUri = "https://youtu.be/YCdyUoyEF_8")
            }
            item {
                VideoListItem(title = "Architecture Components", videoUri = "https://youtu.be/Mn6LhYdnyLs")
            }
            item {
                VideoListItem(title = "Realm DB", videoUri = "https://youtu.be/ntnJdEm2au4")
            }
            item {
                VideoListItem(title = "Tests: JUnit: ScoreModes", videoUri = "https://youtu.be/BuG9G-qJ5zM")
            }
            item {
                VideoListItem(title = "Tests: JUnit: DataSource", videoUri = "https://youtu.be/mSekyrEW4QM")
            }
            item {
                VideoListItem(title = "Tests: JUnit: RealmDbDataModel", videoUri = "https://youtu.be/bl7ZIsCpbP4")
            }
            item {
                VideoListItem(title = "Tests: JUnit: Repo", videoUri = "https://youtu.be/5E_heGeIXyQ")
            }
            item {
                VideoListItem(title = "Tests: JUnit: DomainModel", videoUri = "https://youtu.be/QIzF1MFiLHM")
            }
            item {
                VideoListItem(title = "Tests: ViewModels", videoUri = "https://youtu.be/kpOmvfkUnas")
            }
            item {
                VideoListItem(title = "Tests: Espresso", videoUri = "https://youtu.be/0GSlcdj0JRY")
            }

            item { VideosTypeHeader(typeText = "QT/QML UI Library") }
            item {
                VideoListItem(title = "DeNovix Life Sciences Lab Products", videoUri = "https://youtu.be/C4cQgTu-7wc")
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