package com.mungai.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Accessibility
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mungai.common.freshness
import com.mungai.common.trimSentence
import com.mungai.common.removeExtraSpaces
import com.mungai.domain.model.Article
import com.mungai.home.R
import com.mungai.infor_m_e.ui.theme.Ivory
import com.mungai.ui.Pill

@Composable
fun HeadlineItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (String) -> Unit
) {

    val context = LocalContext.current

    Surface(
        modifier = modifier
            .height(350.dp)
            .width(300.dp),
        tonalElevation = 12.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(data = article.urlToImage)
                    .crossfade(true)
                    .error(R.drawable.kotlin)
                    .placeholder(com.mungai.ui.R.drawable.loading_animation)
                    .build(),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(175.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.DarkGray.copy(alpha = 0.6f),
                                Color.DarkGray.copy(alpha = 0.6f),
                                Color.DarkGray.copy(alpha = 0.6f)
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .clickable {
                        onClick(article.url)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = article.title.removeExtraSpaces(),
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Ivory
                    )

                    article.description?.let { description ->
                        Text(
                            text = description.removeExtraSpaces(),
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Start,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            color = Ivory.copy(alpha = 0.8f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        article.author?.let {
                            Pill(
                                icon = Icons.Rounded.Accessibility,
                                info = it.removeExtraSpaces().trimSentence(),
                                decoration = Triple(
                                    first = 15.dp,
                                    second = 15.sp,
                                    third = Ivory.copy(alpha = 0.8f)
                                ),
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                        Pill(
                            icon = Icons.Rounded.Schedule,
                            info = article.publishedAt.freshness(),
                            decoration = Triple(
                                first = 15.dp,
                                second = 15.sp,
                                third = Ivory.copy(alpha = 0.8f)
                            ),
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
    }
}
