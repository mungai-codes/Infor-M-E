package com.mungai.data.remote

import com.mungai.data.remote.model.ArticleDto

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)
