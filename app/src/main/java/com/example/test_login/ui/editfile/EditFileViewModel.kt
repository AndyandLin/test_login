package com.example.test_login.ui.editfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 定義一個繼承自 ViewModel 的類別 EditFileViewModel
class EditFileViewModel : ViewModel() {

    private val _formItems = MutableLiveData<List<FormItem>>().apply {
        value = listOf(
            FormItem("TTPC_SA_20241007080001", "2024-10-07 07:59", "chwu", "編輯", "刪除", "下載"),
            FormItem("TTPC_SA_20241008103614", "2024-10-08 10:35", "chwu", "編輯", "刪除", "下載"),
            FormItem("TTPC_SA_20241014131514", "2024-10-14 13:14", "chwu", "編輯", "刪除", "下載")
            // 可以繼續添加更多項目
        )
    }
    val formItems: LiveData<List<FormItem>> = _formItems
}