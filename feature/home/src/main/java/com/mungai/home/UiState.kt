package com.mungai.home

import com.mungai.domain.model.Article

data class UiState(
    val topHeadlines: List<Article> = emptyList(),
    val loadingTopHeadlines: Boolean = false,
    val topHeadlinesError: String? = null,
    val currentCategory: Int = 0,
    val categoryResults: List<Article> = emptyList(),
    val loadingCategoryResults: Boolean = false,
    val categoryResultsError: String? = null,
    val searchQuery: String = ""
)
