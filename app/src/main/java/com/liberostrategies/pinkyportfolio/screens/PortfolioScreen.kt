package com.liberostrategies.pinkyportfolio.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.liberostrategies.pinkyportfolio.R
import com.liberostrategies.pinkyportfolio.ui.theme.KotlinLetterK

sealed class PortfolioScreen(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    companion object {
        val screens = listOf(
            Resume, // Home screen
            Kotlin,
            Videos,
            Match
        )

        const val route_kotlin = "kotlin"
        const val route_resume = "resume"
        const val route_videos = "videos"
        const val route_match = "match"
    }

    private object Kotlin : PortfolioScreen(
        route_kotlin,
        R.string.kotlin_timeline,
        KotlinLetterK,
    )

    private object Resume : PortfolioScreen(
        route_resume,
        R.string.resume,
        Icons.Filled.Home
    )

    private object Videos : PortfolioScreen(
        route_videos,
        R.string.videos,
        Icons.Filled.PlayArrow
    )

    private object Match : PortfolioScreen(
        route_match,
        R.string.match_skills,
        Icons.Filled.Star
    )
}