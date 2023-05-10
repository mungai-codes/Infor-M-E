package com.mungai.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attribution
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mungai.common.getFreshness
import com.mungai.domain.model.Article

@Composable
fun Article(
    modifier: Modifier = Modifier,
    article: Article
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .clip(RoundedCornerShape(16.dp)),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .error(R.drawable.error)
                    .placeholder(R.drawable.loading_animation)
                    .build(),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 3,
                fontFamily = FontFamily.Serif,
                overflow = TextOverflow.Ellipsis
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                article.author?.let {
                    Pill(
                        modifier = Modifier.align(Alignment.CenterStart),
                        icon = Icons.Rounded.Attribution,
                        info = it
                    )
                }
                Pill(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    icon = Icons.Rounded.Schedule,
                    info = getFreshness(article.publishedAt)
                )
            }
        }
    }
}

@Composable
fun ArticleShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.3f)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
        )
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .shimmerEffect()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .height(13.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.weight(0.4f))
                Box(
                    modifier = Modifier
                        .weight(0.2f)
                        .height(13.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}