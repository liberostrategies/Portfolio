package com.liberostrategies.pinkyportfolio.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.liberostrategies.pinkyportfolio.R

sealed class PortfolioScreen(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    companion object {
        val screens = listOf(
            Home,
            Resume,
//            Match  // TODO: TBD
        )

        const val route_home = "home"
        const val route_resume = "resume"
        const val route_match = "match"
    }

    private object Home : PortfolioScreen(
        route_home,
        R.string.kotlin_timeline,
        Icons.Filled.Home
    )

    private object Resume : PortfolioScreen(
        route_resume,
        R.string.resume,
        Icons.Filled.List
    )

    private object Match : PortfolioScreen(
        route_match,
        R.string.match_skills,
        Icons.Filled.Star
    )
}