package com.mungai.search.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mungai.domain.model.Article
import com.mungai.search.R

@Composable
fun Body(
    modifier: Modifier = Modifier,
    loading: Boolean,
    emptyResults: Boolean,
    error: String?,
    articles: List<Article>
) {

    if (loading) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(8) {
                com.mungai.ui.ArticleShimmer()
            }
        }
    } else if (error != null) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .sizeIn(maxWidth = 150.dp),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif
                )
            }
        }

    } else if (emptyResults) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_results_found),
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(articles, key = { article -> article.title }) { article ->
                com.mungai.ui.Article(article = article)
            }
        }
    }
}