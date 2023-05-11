package com.mungai.infor_m_e

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mungai.home.HomeScreen
import com.mungai.infor_m_e.ui.theme.InforMETheme
import com.mungai.search.SearchScreen
import com.mungai.search.components.SearchBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InforMETheme {
                //SearchScreen()
                HomeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    InforMETheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(value = "", onValueChange = {}) {

            }
        }
    }
}



