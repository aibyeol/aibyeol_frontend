package com.example.sunflower.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyViewModel : ViewModel() {
    // MutableLiveData to manage the state of the button
    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> = _imageUrls

    private val _scenarioUrls = MutableLiveData<List<String>>()
    val scenarioUrls: LiveData<List<String>> = _scenarioUrls
    // Function to update button enabled state
    fun setButtonEnabled(isEnabled: Boolean) {
        Log.d("SurveyViewModel", "setButtonEnabled called with: $isEnabled")
        _isButtonEnabled.value = isEnabled
    }

    fun setImageUrls(urls: List<String>) {
        Log.d("SurveyViewModel", "setImageUrl called with: $urls")
        _imageUrls.value = urls
    }

    fun setScenarioUrls(urls: List<String>) {
        Log.d("SurveyViewModel", "setScenarioUrl called with: $urls")
        _scenarioUrls.value = urls
    }
}
