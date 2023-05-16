package com.mungai.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mungai.home.components.CategoryItem
import com.mungai.home.components.Header
import com.mungai.home.components.TopHeadlines
import com.mungai.ui.CategoryItemShimmer
import com.mungai.ui.CategoryTabs
import com.mungai.ui.SearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val state: UiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val lazyListState = rememberLazyListState()
    var scrollToTop by remember { mutableStateOf(false) }
    var isSearchVisible by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isSearchVisible) {
        if (isSearchVisible) {
            scrollToTop = true
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DismissibleDrawerSheet(
                drawerShape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Navigation Drawer")
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                Header(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onMenu = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onNotifications = { /*TODO*/ },
                    onSearch = {
                        isSearchVisible = !isSearchVisible
                        lazyListState.firstVisibleItemIndex
                    }
                )
            },
        ) { innerPadding ->


            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = innerPadding.calculateBottomPadding(),
                        top = innerPadding.calculateTopPadding()
                    )
                    .animateContentSize(
                        animationSpec = tween(500)
                    ),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    AnimatedVisibility(
                        visible = isSearchVisible,
                        enter = fadeIn(
                            animationSpec = tween(500)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(500)
                        ),
                    ) {
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = state.searchQuery,
                            onValueChange = viewModel::updateSearchQuery,
                            onSearch = { navController.navigate("search?query=${state.searchQuery}") }
                        )
                    }
                }

                item {
                    TopHeadlines(
                        articles = state.topHeadlines,
                        loading = state.loadingTopHeadlines,
                        error = state.topHeadlinesError,
                        onClick = { url ->
                            navController.navigate("details?url=${url}")
                        }
                    )
                }


                item {
                    CategoryTabs(
                        currentCategory = state.currentCategory,
                        updateCategory = viewModel::updateCategory,
                        onSearch = { viewModel.getNewsByCategory() }
                    )
                }

                item {
                    if (state.loadingCategoryResults) {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(count = 2),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(256.dp)
                        ) {
                            items(4) {
                                CategoryItemShimmer(modifier = Modifier.fillParentMaxWidth(fraction = 0.9f))
                            }
                        }
                    } else if (state.categoryResultsError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.categoryResultsError!!)
                        }
                    } else {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(count = 2),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(256.dp)
                        ) {
                            items(state.categoryResults) { article ->
                                CategoryItem(
                                    modifier = Modifier.fillParentMaxWidth(fraction = 0.9f),
                                    article = article,
                                    onClick = { url ->
                                        navController.navigate("details?url=${url}")
                                    }
                                )
                            }
                        }
                    }
                }
            }

            SideEffect {
                scope.launch {
                    if (scrollToTop) {
                        lazyListState.scrollToItem(0)
                        scrollToTop = false
                    }
                }
            }

        }
    }
}