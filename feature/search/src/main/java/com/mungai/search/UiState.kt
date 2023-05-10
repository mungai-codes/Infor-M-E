package com.mungai.search

import com.mungai.domain.model.Article

data class UiState(
    val articles: List<Article> = emptyList(),
    val loading: Boolean = false,
    val emptyResults: Boolean = false,
    val errorMessage: String? = null,
    val query: String = ""
)
