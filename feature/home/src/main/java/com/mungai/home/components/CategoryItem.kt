package com.mungai.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attribution
import androidx.compose.material.icons.rounded.Source
import androidx.compose.material3.Surface
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
import com.mungai.common.removeExtraSpaces
import com.mungai.common.trimSentence
import com.mungai.domain.model.Article
import com.mungai.ui.Pill

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .height(120.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .error(com.mungai.ui.R.drawable.kotlin)
                        .placeholder(com.mungai.ui.R.drawable.loading_animation)
                        .build(),
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onClick(article.url) },
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .weight(0.6f)
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
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { onClick(article.url) }
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
                            info = it.removeExtraSpaces().trimSentence()
                        )
                    }
                    Pill(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 4.dp),
                        icon = Icons.Rounded.Source,
                        info = article.source.name.removeExtraSpaces().trimSentence()
                    )
                }
            }
        }
    }
}


