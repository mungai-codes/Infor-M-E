package com.mungai.details

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mungai.details.components.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: ArticleDetailsViewModel = hiltViewModel(),
    navController: NavController
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        val isLoading = remember { mutableStateOf(true) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {

            item {
                AndroidView(factory = { context ->
                    WebView(context).apply {

                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        settings.javaScriptEnabled = true

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView, url: String,
                                favicon: Bitmap?
                            ) {
                                isLoading.value = true
                            }

                            override fun onPageFinished(
                                view: WebView, url: String
                            ) {
                                isLoading.value = false
                            }
                        }


                        loadUrl(state.articleUrl)
                    }
                }, update = { webView ->
                    webView.loadUrl(state.articleUrl)
                })

                if (isLoading.value) {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}