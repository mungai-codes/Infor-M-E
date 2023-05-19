package com.mungai.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungai.domain.repository.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


   fun getTheme() {
        dataStoreRepo.getTheme(_uiState.value.isDarkTheme).onEach { isDarkMode ->
            when (isDarkMode) {
                true -> {
                    _uiState.update { it.copy(isDarkTheme = true) }
                }

                false -> {
                    _uiState.update { it.copy(isDarkTheme = false) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveTheme(isDarkThemeEnabled: Boolean) {
        viewModelScope.launch {
            dataStoreRepo.saveTheme(isDarkThemeEnabled)
        }
    }

}