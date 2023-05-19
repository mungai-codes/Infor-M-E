package com.mungai.infor_m_e

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungai.domain.repository.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val _themeState = MutableStateFlow(ThemeState())
    val themeState = _themeState.asStateFlow()

    fun getTheme(isDarkTheme: Boolean) {
        dataStoreRepo.getTheme(isDarkTheme).onEach { isDarkMode ->
            when (isDarkMode) {
                true -> {
                    _themeState.update { it.copy(isDarkTheme = true) }
                }

                false -> {
                    _themeState.update { it.copy(isDarkTheme = false) }
                }
            }

        }.launchIn(viewModelScope)
    }
}