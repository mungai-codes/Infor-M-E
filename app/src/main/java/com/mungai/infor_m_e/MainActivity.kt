package com.mungai.infor_m_e

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mungai.details.DetailsScreen
import com.mungai.home.HomeScreen
import com.mungai.infor_m_e.notifications.NotificationWorker
import com.mungai.infor_m_e.ui.theme.InforMETheme
import com.mungai.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workManager = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 60,
            TimeUnit.MINUTES
        )
            .addTag("notification_request")
            .setInitialDelay(Duration.ofMinutes(15))
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "notification worker",
            ExistingPeriodicWorkPolicy.UPDATE,
            notificationRequest
        )
        setContent {

            val viewModel: MainViewModel = hiltViewModel()
            val state = viewModel.themeState.collectAsState().value

            val systemTheme =
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        true
                    }

                    Configuration.UI_MODE_NIGHT_NO -> {
                        false
                    }

                    else -> {
                        false
                    }
                }

            LaunchedEffect(key1 = state.isDarkTheme) {
                viewModel.getTheme(systemTheme)
            }


            InforMETheme(darkTheme = state.isDarkTheme) {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable(
                        route = "home",
                        enterTransition = {
                            fadeIn(animationSpec = tween(2000))
                        }
                    ) {
                        HomeScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "search?query={query}",
                        enterTransition = {
                            fadeIn(animationSpec = tween(2000))
                        },
                        arguments = listOf(
                            navArgument(name = "query") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        SearchScreen(navController = navController)
                    }
                    composable(
                        route = "details?url={url}",
                        enterTransition = {
                            fadeIn(animationSpec = tween(2000))
                        },
                        //arguments = listOf(navArgument(name = "url") { type = NavType.StringType }),
                        deepLinks = listOf(navDeepLink {
                            uriPattern = "myapp://mungai-codes.com/details?url={url}"
                        })
                    ) {

                        DetailsScreen(navController = navController)
                    }
                }
            }
        }
    }
}




