package com.example.test_login.ui.editfile

// 定義數據類 FormItem，包含表單的屬性
data class FormItem(
    val questionId: String,  // 問卷編號
    val date: String,        // 日期
    val collector: String,   // 收案人員
    val edit: String,        // 編輯操作
    val delete: String,      // 刪除操作
    val download: String      // 下載操作
)