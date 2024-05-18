package com.liberostrategies.pinkyportfolio.screens.videos

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

const val videoUriTeamsPlay = "https://s3.us-east-2.amazonaws.com/liberostrategies.com/assets/videos/portfolio/TeamsPlayAndroid.mov"
const val videoUriRealmDb = "https://s3.us-east-2.amazonaws.com/liberostrategies.com/assets/videos/portfolio/RealmDB.mov"
@Composable
fun VideosScreen(context: Context) {

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
                VideoListItem(title = "Teams Play Demo", videoUri = videoUriTeamsPlay)
            }
            item {
                VideoListItem(title = "Realm DB", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "JUnit Tests", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Lint Unit Test Coverage", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "MockK Tests", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Fake Google Play Billing API Test", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Espresso Test", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Use Case Tests using Truth", videoUri = videoUriRealmDb)
            }
            item {
                VideoListItem(title = "Architecture Components Dependencies", videoUri = videoUriRealmDb)
            }
        }

        val isLandscape = false
        //val context = LocalContext.current
        val activity = context.findActivity()
        val enterFullscreen = {
            if (activity != null) {
                activity.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            }
        }
        val exitFullscreen = {
            if (activity != null) {
                @SuppressLint("SourceLockedOrientationActivity")
                // Will reset to SCREEN_ORIENTATION_USER later
                activity.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
            }
        }
    }
}

@Composable
fun VideoListItem(title: String, videoUri: String) {

    var openVideo by remember { mutableStateOf(false) }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            //containerColor = Color.Red,
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp),
        )
    }

    if (openVideo) {
        Dialog (
            onDismissRequest = { openVideo = false }
        ) {
            VideoPlayer(videoUri = videoUri, onClose = {
                openVideo = false
            })
        }
    }

}
