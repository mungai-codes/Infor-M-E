package com.mungai.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungai.common.Constants
import com.mungai.common.Resource
import com.mungai.domain.repository.InformeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: InformeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNewsByCategory()
        getTopHeadlines()
    }

    private var searchJob: Job? = null

    private fun getTopHeadlines() {
        viewModelScope.launch {
            repository.getTopHeadlines().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(loadingTopHeadlines = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                topHeadlines = result.data ?: emptyList(),
                                loadingTopHeadlines = false,
                                topHeadlinesError = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                loadingTopHeadlines = false,
                                topHeadlinesError = result.message
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun getNewsByCategory() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repository.getNewsByCategory(Constants.categories[_uiState.value.currentCategory])
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(loadingCategoryResults = true) }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    categoryResults = result.data ?: emptyList(),
                                    loadingCategoryResults = false,
                                    categoryResultsError = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    loadingCategoryResults = false,
                                    categoryResultsError = result.message
                                )
                            }
                        }
                    }
                }.launchIn(this)
        }
    }

    fun updateCategory(input: Int) {
        _uiState.update { it.copy(currentCategory = input) }
    }

    fun updateSearchQuery(input: String) {
        _uiState.update { it.copy(searchQuery = input) }
    }

}