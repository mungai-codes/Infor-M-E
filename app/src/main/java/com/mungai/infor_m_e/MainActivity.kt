package com.mungai.infor_m_e

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
            InforMETheme(darkTheme = true) {
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
                        HomeScreen(navController = navController)
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
                        SearchScreen()
                    }
                }
            }
        }
    }
}




