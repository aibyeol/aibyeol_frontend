package com.example.sunflower.ui.screen

import androidx.lifecycle.ViewModel
import com.example.sunflower.data.StudyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudyUiState())
    val uiState: StateFlow<StudyUiState> = _uiState.asStateFlow()

    fun loadImage() {
        _uiState.update { currentState ->
            currentState.copy(
                isImageLoaded = true
            )
        }
    }
}