package com.mungai.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mungai.infor_m_e.ui.theme.InforMETheme

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onMenu: () -> Unit,
    onNotifications: () -> Unit,
    onSearch: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onMenu
        ) {
            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "menu")
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier,
                onClick = onNotifications
            ) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "notifications"
                )
            }
            IconButton(
                modifier = Modifier,
                onClick = onSearch
            ) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "search")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    InforMETheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Header(
                modifier = Modifier.padding(horizontal = 16.dp),
                onMenu = {}, onNotifications = {}
            ) {

            }
        }
    }
}