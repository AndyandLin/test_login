package com.example.test_login.ui.healthsurvey

import androidx.lifecycle.ViewModel

// 定義 HealthSurveyViewModel 繼承自 ViewModel
class HealthSurveyViewModel : ViewModel() {
    // 定義一個可變的列表，用來存放調查問卷的答案
    private val _surveyAnswers = mutableListOf<String?>()
    // 提供一個不可變的公開訪問接口，用來獲取答案列表
    val surveyAnswers: List<String?> get() = _surveyAnswers

    // 方法：將調查問卷的答案添加到列表中
    fun addSurveyAnswer(answer: String?) {
        _surveyAnswers.add(answer)
    }

    // 方法：清空所有的調查問卷答案
    fun clearSurveyAnswers() {
        _surveyAnswers.clear()
    }
}
