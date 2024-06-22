package com.liberostrategies.pinkyportfolio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.google.firebase.firestore.CollectionReference
import com.liberostrategies.pinkyportfolio.Job
import com.liberostrategies.pinkyportfolio.WristAwayApp
import com.liberostrategies.pinkyportfolio.Year
import com.liberostrategies.pinkyportfolio.data.source.JobQualificationsDoNotExistException
import com.liberostrategies.pinkyportfolio.domain.model.JobQualificationDomainModel
import com.liberostrategies.pinkyportfolio.ui.viewmodels.MatchViewModel

@Composable
fun KotlinTimelineScreen(
    matchViewModel: MatchViewModel,
    collectionJobQuals: CollectionReference,
) {
    var size by remember { mutableIntStateOf(0) }

    val mapJobQualifications = JobQualifications.MAP_JOB_QUALIFICATIONS
    mapJobQualifications.forEach { (categoryKey, _) ->

    val docCertifications = collectionJobQuals.document(categoryKey)
    val listQualifications = mutableListOf<JobQualificationDomainModel>()

    // NOTE: Could not figure out how to put this Firebase DB read into FirebaseDataSource.kt.
    // Kept losing items in listQualifications after get().
    docCertifications.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                var i = 0
                while (document.data?.get("$i") != null) {
                    val q = document.data?.get("$i").toString()
                    listQualifications.add(JobQualificationDomainModel(categoryKey, q))
                    matchViewModel.addJobQualification(categoryKey, q)
                    i++
                    size = i
                }
            } else {
                Logger.e("KotlinTimelineScreen") { "No such document" }
            }
            matchViewModel.setInitialQualificationsSize(matchViewModel.getJobQualifications().size)
        }
        .addOnFailureListener { exception ->
            Logger.e("KotlinTimelineScreen") { "Get failed with $exception" }
            throw JobQualificationsDoNotExistException(exception.toString())
        }

    } // end mapJobQualifications.forEach

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
    ) {
        items(1) {
            Column(
            ) {
                Job(
                    "Principal Application Software Developer",
                    "ASRC Federal",
                    "Java,C",
                    height = 150.dp,
                )
                Job(
                    "Software Engineer",
                    "DeNovix",
                    "C++, Qt, QML",
                    height = 620.dp,
                )
                Job(
                    "AndroidCert AppDev",
                    "(AND-401)",
                    height = 55.dp,
                )
            }
        }

        items(1){
            Column(
                modifier = Modifier.background(
                    Color(
                        red = 0,
                        green = 104,
                        blue = 176
                    )
                )
            ) {
                for (y in 2024 downTo 2017) {
                    Year(y)
                }
            }
        }

        items(1) {
            WristAwayApp(
                "WristAway v1.0", "https://github.com/liberostrategies/WristAwayJ", "Java, Views, Wear OS",
                background = Color(red = 0, green = 170, blue = 149),
                height = 400.dp,
                topPadding = 320.dp,
            )

            WristAwayApp(
                "WristAway v1.1", "https://github.com/liberostrategies/WristAwayK", "Kotlin, Views, Wear OS",
                appLinks = "Download App from \nhttps://play.google.com/store/apps/details?id=com.liberostrategies.wristawayscoring",
                background = Color(red = 165, green = 23, blue = 232),
                height = 315.dp,
                topPadding = 5.dp,
            )
        }

        items(1) {
            WristAwayApp(
                "WristAway Pro v1.0", "https://github.com/liberostrategies/WristAwayKMP", "Kotlin, Compose Multiplatform",
                background = Color(red = 106, green = 43, blue = 146),
                height = 110.dp,
                topPadding = 5.dp,
            )
        }
    }
}