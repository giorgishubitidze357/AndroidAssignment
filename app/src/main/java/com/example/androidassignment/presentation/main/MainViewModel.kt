package com.example.androidassignment.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.domain.repository.ItemRepository
import com.example.androidassignment.domain.usecase.GetHierarchyUseCase
import com.example.androidassignment.presentation.main.navigator.MainNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: MainNavigator,
    private val getHierarchyUseCase: GetHierarchyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState : StateFlow<MainScreenState> =  _uiState.asStateFlow()

    private val _intents = MutableSharedFlow<MainScreenIntent>()

    init {
        Log.d("HiltDebug", "MainViewModel initialized. Calling fetchContent().")

        viewModelScope.launch {
            _intents.collect { intent ->
                    handleIntent(intent)
            }
        }

        fetchContent()
    }

    fun handleIntent(intent: MainScreenIntent) {
        when(intent) {
            MainScreenIntent.FetchContent -> fetchContent()
            is MainScreenIntent.ImageClicked -> {
                navigateToImageDetail(intent.imageUrl, intent.imageTitle)
            }
            MainScreenIntent.ErrorMessageShown -> _uiState.update { it.copy(snackbarMessage = null) }
        }
    }

    fun sendIntent(intent: MainScreenIntent) {
        viewModelScope.launch { _intents.emit(intent) }
    }

    fun navigateToImageDetail(imageUrl: String, imageTitle: String) {
        navigator.navigateToImageDetail(imageUrl, imageTitle)
    }

    private fun fetchContent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = getHierarchyUseCase()

            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { items ->
                        currentState.copy(
                            isLoading = false,
                            contentItems = items,
                            errorMessage = null,
                            snackbarMessage = null
                        )
                    },
                    onFailure = { throwable ->
                        currentState.copy(
                            isLoading = false,
                            contentItems = null,
                            errorMessage = throwable.localizedMessage ?: "Unknown error occurred.",
                            snackbarMessage = null
                        )
                    }
                )
            }
        }
    }

}
