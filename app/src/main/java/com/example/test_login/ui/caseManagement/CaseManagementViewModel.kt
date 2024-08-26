package com.example.test_login.ui.caseManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CaseManagementViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is CaseManagement"
    }
    val text: LiveData<String> = _text
}
