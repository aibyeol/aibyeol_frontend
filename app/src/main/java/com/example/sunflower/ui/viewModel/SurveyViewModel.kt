package com.example.sunflower.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyViewModel : ViewModel() {
    // MutableLiveData to manage the state of the button
    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _imageUrl = MutableLiveData<String?>(null)
    val imageUrl: LiveData<String?> = _imageUrl

    // Function to update button enabled state
    fun setButtonEnabled(isEnabled: Boolean) {
        Log.d("SurveyViewModel", "setButtonEnabled called with: $isEnabled")
        _isButtonEnabled.value = isEnabled
    }

    fun setImageUrl(url: String?) {
        Log.d("SurveyViewModel", "setImageUrl called with: $url")
        _imageUrl.value = url
    }
}
