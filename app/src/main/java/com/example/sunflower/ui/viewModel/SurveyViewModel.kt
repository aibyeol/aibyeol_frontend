package com.example.sunflower.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyViewModel : ViewModel() {
    // MutableLiveData to manage the state of the button
    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    // Function to update button enabled state
    fun setButtonEnabled(isEnabled: Boolean) {
        _isButtonEnabled.value = isEnabled
    }
}
