package com.liberostrategies.pinkyportfolio.screens.videos

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import co.touchlab.kermit.Logger

@Composable
fun Context.findActivity(): Activity? = when (this) {
    is Activity       -> this
    is ContextWrapper -> baseContext.findActivity()
    else              -> null
}

@Composable
fun Context.SetScreenOrientation(orientation: Int) {
    val activity = this.findActivity() ?: return
    activity.requestedOrientation = orientation
    if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        //hideSystemUi()
        Logger.d("VideoPlayer") { "Orientation landscape" }
    } else {
        //showSystemUi()
        Logger.d("VideoPlayer") { "Orientation portrait" }
    }
}

@Composable
fun VideoPlayer(
    videoUri: String,
    onClose: () -> Unit
) {

    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build()

    val mediaSource = remember(videoUri) {
        MediaItem.fromUri(videoUri)
    }

    //context.SetScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    exoPlayer.addListener(
        object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    // Active playback.
                    Logger.d("VideoPlayer") { "ExoPlayer is playing $videoUri" }
                    //PlayVideo(videoUri)

                } else {
                    // Not playing because playback is paused, ended, suppressed, or the player
                    // is buffering, stopped or failed. Check player.playWhenReady,
                    // player.playbackState, player.playbackSuppressionReason and
                    // player.playerError for details.
                    Logger.d("VideoPlayer") { "ExoPlayer is not playing $videoUri" }
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .background(Color.Cyan.copy(alpha = 0.8f))
            .fillMaxSize()
//            .fillMaxWidth()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
//                .fillMaxWidth()
//                .fillMaxSize(),
                    ,
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer


                    // Full screen
//                    layoutParams =
////                        FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                    )

                    /*
                    setFullscreenButtonClickListener {
                        //  Handle full screen callback based on dialog visibility
                        if(!dialog.isShowing){
                            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            dialog = object : Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen){
                                override fun onBackPressed() {
                                    StyledPlayerView.switchTargetView(player, fullScreenPlayerView, playerView)
                                    this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                                    // You can change the drawable of the image button.
                                    playerView.findViewById<ImageButton>(com.google.android.exoplayer2.ui.R.id.exo_fullscreen)
                                        .setImageResource(R.drawable.ic_fullscreen_expand)

                                    super.onBackPressed()
                                }
                            }
                            dialog?.addContentView(
                                fullScreenPlayerView,
                                ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            )
                            dialog?.show()
                            StyledPlayerView.switchTargetView(player, playerView, fullScreenPlayerView)
                        } else {
                            StyledPlayerView.switchTargetView(player, fullScreenPlayerView, playerView)
                            this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            dialog?.dismiss()
                        }
                    }
    */


                }
            },
        )
        IconButton(
            onClick = { onClose() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
    }
}


