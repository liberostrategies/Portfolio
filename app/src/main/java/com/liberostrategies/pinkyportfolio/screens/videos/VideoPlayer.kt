package com.liberostrategies.pinkyportfolio.screens.videos

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import co.touchlab.kermit.Logger

@Composable
fun VideoPlayer(
    videoUri: String
) {

    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build()

    val mediaSource = remember(videoUri) {
        MediaItem.fromUri(videoUri)
    }

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

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
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

                // Full screen
                layoutParams =
                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

 */

            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
