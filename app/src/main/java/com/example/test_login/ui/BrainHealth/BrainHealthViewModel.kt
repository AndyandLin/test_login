package com.example.test_login.ui.BrainHealth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// 定義 HealthSurveyViewModel 繼承自 ViewModel
class BrainHealthViewModel : ViewModel() {

    // 使用 MutableLiveData 來保存和追蹤 HealthSurveyData 的變化
    private val _surveyData = MutableLiveData<BrainHealthData>().apply {
        value = BrainHealthData() // 初始化為一個空的問卷數據
    }

    // 提供只讀的 LiveData 給外部使用
    val surveyData: LiveData<BrainHealthData> get() = _surveyData

    // 更新姓名
    fun setName(name: String) {
        _surveyData.value?.let {
            it.name = name
            _surveyData.value = it // 更新 LiveData
        }
    }

    // 更新性別
    fun setGender(gender: String) {
        _surveyData.value?.let {
            it.gender = gender
            _surveyData.value = it
        }
    }

    // 更新出生年月日
    fun setBirthdate(birthdate: String) {
        _surveyData.value?.let {
            it.birthdate = birthdate
            _surveyData.value = it
        }
    }

    // 更新聯絡電話
    fun setPhone(phone: String) {
        _surveyData.value?.let {
            it.phone = phone
            _surveyData.value = it
        }
    }

    // 更新通訊地址
    fun setAddress(address: String) {
        _surveyData.value?.let {
            it.address = address
            _surveyData.value = it
        }
    }

    // 更新婚姻狀態
    fun setMaritalStatus(maritalStatus: String) {
        _surveyData.value?.let {
            it.maritalStatus = maritalStatus
            _surveyData.value = it
        }
    }

    // 更新教育程度
    fun setEducationLevel(educationLevel: String) {
        _surveyData.value?.let {
            it.educationLevel = educationLevel
            _surveyData.value = it
        }
    }

    // 更新經濟狀況
    fun setEconomicStatus(economicStatus: String) {
        _surveyData.value?.let {
            it.economicStatus = economicStatus
            _surveyData.value = it
        }
    }

    // 更新收案日期
    fun setCaseDate(caseDate: String) {
        _surveyData.value?.let {
            it.caseDate = caseDate
            _surveyData.value = it
        }
    }

    // 更新與家人同住狀態
    fun setLivingWithFamily(livingWithFamily: String) {
        _surveyData.value?.let {
            it.livingWithFamily = livingWithFamily
            _surveyData.value = it
        }
    }

    // 更新工作狀態
    fun setJobStatus(jobStatus: String) {
        _surveyData.value?.let {
            it.jobStatus = jobStatus
            _surveyData.value = it
        }
    }

    // 更新宗教信仰
    fun setReligion(religion: String) {
        _surveyData.value?.let {
            it.religion = religion
            _surveyData.value = it
        }
    }

    // 更新收案地區
    fun setRegion(region: String) {
        _surveyData.value?.let {
            it.region = region
            _surveyData.value = it
        }
    }
    // 新增問題答案的設定方法
    fun setQuestionAnswer(questionNumber: Int, isYes: Boolean) {
        _surveyData.value?.let {
            when (questionNumber) {
                1 -> it.q1 = isYes
                2 -> it.q2 = isYes
                3 -> it.q3 = isYes
                4 -> it.q4 = isYes
                5 -> it.q5 = isYes
                6 -> it.q6 = isYes
                7 -> it.q7 = isYes
                8 -> it.q8 = isYes
                9 -> it.q9 = isYes
                10 -> it.q10 = isYes
                11 -> it.q11 = isYes
                12 -> it.q12 = isYes
                13 -> it.q13 = isYes
                14 -> it.q14 = isYes
                15 -> it.q15 = isYes
            }
            _surveyData.value = it
        }
    }

    // 取得特定問題的答案
    fun getQuestionAnswer(questionNumber: Int): Boolean {
        return _surveyData.value?.let {
            when (questionNumber) {
                1 -> it.q1
                2 -> it.q2
                3 -> it.q3
                4 -> it.q4
                5 -> it.q5
                6 -> it.q6
                7 -> it.q7
                8 -> it.q8
                9 -> it.q9
                10 -> it.q10
                11 -> it.q11
                12 -> it.q12
                13 -> it.q13
                14 -> it.q14
                15 -> it.q15
                else -> false
            }
        } ?: false
    }

    // 檢查是否所有必填欄位都已填寫
    fun isSurveyComplete(): Boolean {
        val data = _surveyData.value
        return data?.name?.isNotEmpty() == true &&
                data.gender?.isNotEmpty() == true &&
                data.birthdate?.isNotEmpty() == true &&
                data.phone?.isNotEmpty() == true &&
                data.address?.isNotEmpty() == true &&
                data.maritalStatus?.isNotEmpty() == true &&
                data.educationLevel?.isNotEmpty() == true &&
                data.economicStatus?.isNotEmpty() == true &&
                data.caseDate?.isNotEmpty() == true &&
                data.livingWithFamily?.isNotEmpty() == true &&
                data.jobStatus?.isNotEmpty() == true &&
                data.religion?.isNotEmpty() == true &&
                data.region?.isNotEmpty() == true &&
                // 檢查所有問題是否都有答案
                areAllQuestionsAnswered()
    }

    // 檢查所有問題是否都已回答
    private fun areAllQuestionsAnswered(): Boolean {
        val data = _surveyData.value ?: return false
        return listOf(
            data.q1, data.q2, data.q3, data.q4, data.q5,
            data.q6, data.q7, data.q8, data.q9, data.q10,
            data.q11, data.q12, data.q13, data.q14, data.q15
        ).all { it != null }  // 確保每個問題都有答案（是或否）
    }

    // 取得所有問題的答案
    fun getAllQuestionAnswers(): Map<Int, Boolean> {
        return _surveyData.value?.let { data ->
            mapOf(
                1 to data.q1,
                2 to data.q2,
                3 to data.q3,
                4 to data.q4,
                5 to data.q5,
                6 to data.q6,
                7 to data.q7,
                8 to data.q8,
                9 to data.q9,
                10 to data.q10,
                11 to data.q11,
                12 to data.q12,
                13 to data.q13,
                14 to data.q14,
                15 to data.q15
            )
        } ?: emptyMap()
    }

    // 修改 clearSurvey 方法，確保問題答案也被清除
    fun clearSurvey() {
        _surveyData.value = BrainHealthData().apply {
            q1 = false
            q2 = false
            q3 = false
            q4 = false
            q5 = false
            q6 = false
            q7 = false
            q8 = false
            q9 = false
            q10 = false
            q11 = false
            q12 = false
            q13 = false
            q14 = false
            q15 = false
        }
    }
}
