package com.mungai.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pill(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    info: String,
    decoration: Triple<Dp, TextUnit, Color> = Triple(13.dp, 10.sp, Color(0xFF98989B))
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = info,
            tint = decoration.third,
            modifier = Modifier.size(decoration.first)
        )
        Text(
            text = info,
            fontSize = decoration.second,
            color = decoration.third
        )
    }
}

