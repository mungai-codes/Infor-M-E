package com.mungai.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeadlineItemShimmer(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(350.dp)
            .width(300.dp),
        tonalElevation = 12.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .shimmerEffect())
    }
}