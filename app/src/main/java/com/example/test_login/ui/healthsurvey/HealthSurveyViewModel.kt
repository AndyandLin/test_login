package com.example.test_login.ui.healthsurvey

import androidx.lifecycle.ViewModel

class HealthSurveyViewModel : ViewModel() {
    private val _surveyAnswers = mutableListOf<String?>()
    val surveyAnswers: List<String?> get() = _surveyAnswers

    fun addSurveyAnswer(answer: String?) {
        _surveyAnswers.add(answer)
    }

    fun clearSurveyAnswers() {
        _surveyAnswers.clear()
    }
}
