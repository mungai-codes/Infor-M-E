package com.mungai.home

import com.mungai.domain.model.Article

data class UiState(
    val topHeadlines: List<Article> = emptyList(),
    val loadingTopHeadlines: Boolean = false,
    val topHeadlinesError: String? = null,
    val currentCategory: Int = 0
)
