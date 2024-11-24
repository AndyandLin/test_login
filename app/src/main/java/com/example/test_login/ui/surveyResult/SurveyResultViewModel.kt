package com.example.test_login.ui.surveyResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyResultViewModel : ViewModel() {

    // 使用 LiveData 來儲存調查答案，便於觀察
    private val _surveyAnswers = MutableLiveData<Map<String, String>>(emptyMap())
    val surveyAnswers: LiveData<Map<String, String>> get() = _surveyAnswers

    // 設置調查答案
    fun setSurveyAnswers(answers: Map<String, String>) {
        _surveyAnswers.value = answers // 更新答案
        Log.d("SurveyResultViewModel", "Survey answers set: $answers") // 添加日誌以便於調試
    }

    // 獲取當前調查答案
    fun getSurveyAnswers(): Map<String, String> {
        return _surveyAnswers.value ?: emptyMap() // 確保返回的映射非空
    }

    // 清空調查答案
    fun clearSurveyAnswers() {
        _surveyAnswers.value = emptyMap() // 清空答案
        Log.d("SurveyResultViewModel", "Survey answers cleared") // 添加清空操作的日誌
    }
}
