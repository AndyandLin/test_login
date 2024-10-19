package com.example.test_login.ui.surveyResult

import androidx.lifecycle.ViewModel

class SurveyResultViewModel : ViewModel() {

    private val surveyAnswers: MutableMap<String, String> = mutableMapOf()

    fun setSurveyAnswers(answers: Map<String, String>) {
        surveyAnswers.clear()
        surveyAnswers.putAll(answers)
    }

    fun getSurveyAnswers(): Map<String, String> {
        return surveyAnswers
    }
}
