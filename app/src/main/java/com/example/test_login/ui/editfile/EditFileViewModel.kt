package com.example.test_login.ui.editfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 定義一個繼承自 ViewModel 的類別 EditFileViewModel
class EditFileViewModel : ViewModel() {

    // 定義一個 MutableLiveData 變數，用來存放和觀察文字數據
    private val _text = MutableLiveData<String>().apply {
        // 初始化 MutableLiveData 變數的值為 "Edit file"
        value = "Edit file"
    }
    // 將 MutableLiveData 變數轉換為不可變的 LiveData，提供給外部觀察
    val text: LiveData<String> = _text

    // 定義一個 MutableLiveData 變數，用來存放和觀察表單數據列表
    private val _formItems = MutableLiveData<List<FormItem>>().apply {
        // 初始化表單數據列表，包含多個 FormItem
        value = listOf(
            FormItem("SA", "PUB_SA_10000", "PUB", "Nov. 30, 2023, 1:36 p.m."),
            FormItem("SA", "PUB_SA_10001", "PUB", "Nov. 30, 2023, 1:36 p.m.")
            // 需要時可以繼續添加更多項目
        )
    }
    // 將 MutableLiveData 變數轉換為不可變的 LiveData，提供給外部觀察
    val formItems: LiveData<List<FormItem>> = _formItems
}
