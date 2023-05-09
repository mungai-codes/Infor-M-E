package com.mungai.domain.repository

import com.mungai.common.Resource
import com.mungai.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface InformeRepository {

    fun searchForNews(query: String): Flow<Resource<List<Article>>>
}