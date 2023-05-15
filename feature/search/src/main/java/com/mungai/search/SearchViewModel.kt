package com.mungai.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mungai.common.Resource
import com.mungai.common.UiEvent
import com.mungai.domain.repository.InformeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: InformeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    init {
        savedStateHandle.get<String>("query")?.let { query ->
            if (query != "") {
                searchForNews(query = query)
            }
        }
    }

    fun searchForNews(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repository.searchForNews(query = query).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(loading = true) }
                    }

                    is Resource.Success -> {
                        if (result.data?.isEmpty() == true) {
                            _uiState.update {
                                it.copy(
                                    articles = emptyList(),
                                    loading = false,
                                    errorMessage = null,
                                    emptyResults = true
                                )
                            }
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    "Check Your query or try again later!"
                                )
                            )
                        } else {
                            _uiState.update {
                                it.copy(
                                    articles = result.data!!,
                                    loading = false,
                                    errorMessage = null,
                                    emptyResults = false
                                )
                            }
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    "Showing search results."
                                )
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(loading = false, errorMessage = result.message) }
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                result.message ?: "An Unexpected Error Occurred"
                            )
                        )
                    }
                }
            }.launchIn(this)

        }
    }

    fun updateQuery(input: String) {
        _uiState.update { it.copy(query = input) }
    }

}