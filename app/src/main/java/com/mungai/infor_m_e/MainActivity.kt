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
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mungai.details.DetailsScreen
import com.mungai.home.HomeScreen
import com.mungai.infor_m_e.ui.theme.InforMETheme
import com.mungai.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint


@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
                        arguments = listOf(
                            navArgument(name = "url") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        DetailsScreen(navController = navController)
                    }
                }
            }
        }
    }
}




