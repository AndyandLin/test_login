package com.example.test_login.ui.editfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditFileViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Edit file"
    }
    val text: LiveData<String> = _text

    private val _formItems = MutableLiveData<List<FormItem>>().apply {
        value = listOf(
            FormItem("SA", "PUB_SA_10000","PUB","Nov. 30, 2023, 1:36 p.m."),
            FormItem("SA", "PUB_SA_10001","PUB","Nov. 30, 2023, 1:36 p.m."),
            // Add more items as needed
        )
    }
    val formItems: LiveData<List<FormItem>> = _formItems
}
