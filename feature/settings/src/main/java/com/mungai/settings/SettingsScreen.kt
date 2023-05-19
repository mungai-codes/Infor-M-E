package com.mungai.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state by themeViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.isDarkTheme) {
        themeViewModel.getTheme()
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(IntrinsicSize.Min)
                .width(250.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Dark Theme",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = if (state.isDarkTheme) "Turn off dark theme" else "Turn on dark theme",
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start
                )
            }
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = state.isDarkTheme,
                    onCheckedChange = themeViewModel::saveTheme,
                    thumbContent = {
                        Icon(
                            modifier = Modifier
                                .size(SwitchDefaults.IconSize),
                            imageVector = if (state.isDarkTheme) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                            contentDescription = "Switch Icon"
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
            }
        }
    }
}