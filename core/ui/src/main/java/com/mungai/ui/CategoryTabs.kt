package com.mungai.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mungai.common.Constants.categories


@Composable
fun CategoryTabs(
    modifier: Modifier = Modifier,
    currentCategory: Int,
    updateCategory: (Int) -> Unit,
    onSearch: () -> Unit
) {

    ScrollableTabRow(
        modifier = modifier
            .fillMaxWidth(),
        selectedTabIndex = minOf(categories.size, currentCategory),
        edgePadding = 16.dp,
        divider = {}
    ) {
        categories.forEachIndexed { index, tabName ->
            Tab(
                selected = index == currentCategory,
                onClick = {
                    updateCategory(index)
                    onSearch()
                },
                text = {
                    Text(
                        text = tabName,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                unselectedContentColor = Color(0xFF98989B)
            )
        }
    }
}