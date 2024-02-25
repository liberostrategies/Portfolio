package com.liberostrategies.pinkyportfolio

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import com.liberostrategies.pinkyportfolio.data.repo.IJobQualificationRepository
import com.liberostrategies.pinkyportfolio.data.repo.JobQualificationsRepo
import com.liberostrategies.pinkyportfolio.data.source.FirebaseDataSource
import com.liberostrategies.pinkyportfolio.screens.KotlinTimelineScreen
import com.liberostrategies.pinkyportfolio.screens.MatchScreen
import com.liberostrategies.pinkyportfolio.screens.PortfolioScreen
import com.liberostrategies.pinkyportfolio.screens.ResumeScreen
import com.liberostrategies.pinkyportfolio.ui.theme.PinkyPortfolioTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val db = FirebaseDataSource()
            val repoJobQual = JobQualificationsRepo(db)

            PinkyPortfolioTheme {
                Surface(
                    tonalElevation = 5.dp,
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Pinky Ramos Portfolio") },

                                navigationIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_top_app_bar),
                                        contentDescription = stringResource(id = R.string.portfolio_picture),
                                        Modifier.size(50.dp)
                                    )
                                }
                            )
                        },

                        bottomBar = { PortfolioBottomBar(navController) }

                    ) {
                        //KotlinTimeline() {}
                        PortfolioNavHost(
                            navController = navController,
                            modifier = Modifier.padding(it),
                            context = this,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PortfolioNavHost(
    navController: NavHostController,
    modifier: Modifier,
    context: Context,
) {
    NavHost(
        navController = navController,
        startDestination = PortfolioScreen.route_home,
        modifier = modifier
    ) {
        composable(PortfolioScreen.route_home) {
            KotlinTimelineScreen(
            )
        }
        composable(
            route = PortfolioScreen.route_resume,
        ) {
            ResumeScreen(
                context = context
            )
        }
        composable(PortfolioScreen.route_match) {
            MatchScreen()
        }
    }
}

@Composable
fun PortfolioBottomBar(navController: NavController) {
    BottomAppBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        PortfolioScreen.screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = stringResource(id = screen.label))
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.label)
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun Job(
    title: String,
    company: String,
    tech: String = "",
    height: Dp = 200.dp,
    fontSize: TextUnit = 10.sp
) {
    val textColor = Color.Black
    val fontSizeCompany = 8.sp
    val background = Color.Yellow

    var openJobDetailsDialog by remember { mutableStateOf(false) }
    if (openJobDetailsDialog) {
        Dialog(
            onDismissRequest = { openJobDetailsDialog = false }
        ) {
            OutlinedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = background,
                ),
            ) {
                Text(
                    text = title,
                    color = textColor,
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )
                Text(
                    text = company,
                    color = textColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(5.dp),
                )
                Text(
                    text = tech,
                    color = textColor,
                    fontSize = 16.sp,
                    lineHeight = fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )
            }
        }
    }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = background,
        ),
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(height)
            .border(width = 1.dp, color = Color.Black)
            .padding(1.dp)
            .clickable(true,
                onClick = {
                    Logger.i("Clicked $title")
                    openJobDetailsDialog = true
                }
            )
    ) {
        Text(
            text = title,
            color = textColor,
            fontSize = fontSize,
            lineHeight = fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp),
        )
        Text(
            text = company,
            color = textColor,
            fontSize = fontSizeCompany,
            modifier = Modifier.padding(5.dp),
        )
        Text(
            text = tech,
            color = textColor,
            fontSize = fontSize,
            lineHeight = fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun Year(
    year: Int
) {
    val textColor = Color.White
    Text(
        text = year.toString(),
        textAlign = TextAlign.Center,
        color = textColor,
        fontSize = 12.sp,
        modifier = Modifier
            .border(width = 1.dp, color = Color.Black)
            .fillMaxWidth(1f)
            .padding(top = 36.dp, bottom = 36.dp, start = 5.dp, end = 5.dp),
    )
}

@Composable
fun WristAwayApp(
    version: String,
    repo: String,
    tech: String,
    appLinks: String = "",
    height: Dp = 200.dp,
    background: Color,
    topPadding: Dp = 5.dp,
    fontSize: TextUnit = 8.sp
) {
    val textColor = Color.White

    var openAppDetailsDialog by remember { mutableStateOf(false) }
    if (openAppDetailsDialog) {
        Dialog(
            onDismissRequest = { openAppDetailsDialog = false }
        ) {
            OutlinedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = background,//MaterialTheme.colorScheme.surfaceVariant,
                ),
            ) {
                Text(
                    text = version,
                    color = textColor,
                    modifier = Modifier.padding(5.dp),
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = "$repo\n",
                    lineHeight = 14.sp,
                    color = textColor,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 14.sp
                )
                Text(
                    text = "$tech\n",
                    fontSize = 16.sp,
                    lineHeight = 8.sp,
                    color = textColor,
                    modifier = Modifier.padding(5.dp),
                )
                if (appLinks.isNotEmpty()) {
                    Text(
                        text = appLinks,
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        color = textColor,
                        modifier = Modifier.padding(5.dp),
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.padding(top = topPadding)
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = background,
            ),
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
                .padding(1.dp)
                .clickable(true,
                    onClick = {
                        Logger.i("Clicked $version")
                        openAppDetailsDialog = true
                    }
                )
        ) {
            Text(
                text = version,
                color = textColor,
                modifier = Modifier.padding(5.dp),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
            )
            Text(
                text = tech,
                fontSize = fontSize,
                lineHeight = 8.sp,
                color = textColor,
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}
