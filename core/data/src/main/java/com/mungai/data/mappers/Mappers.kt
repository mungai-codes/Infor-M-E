package com.mungai.data.mappers

import com.mungai.data.remote.model.ArticleDto
import com.mungai.data.remote.model.SourceDto
import com.mungai.domain.model.Article
import com.mungai.domain.model.Source

fun SourceDto.toSource() =
    Source(id, name)

fun ArticleDto.toArticle() =
    Article(source.toSource(), author, title, description, url, urlToImage, publishedAt, content)