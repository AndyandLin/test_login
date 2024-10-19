package com.example.test_login.ui.healthsurvey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_login.ui.healthsurvey.HealthSurveyData


// 定義 HealthSurveyViewModel 繼承自 ViewModel
class HealthSurveyViewModel : ViewModel() {

    // 使用 MutableLiveData 來保存和追蹤 HealthSurveyData 的變化
    private val _surveyData = MutableLiveData<HealthSurveyData>().apply {
        value = HealthSurveyData() // 初始化為一個空的問卷數據
    }

    // 提供只讀的 LiveData 給外部使用
    val surveyData: LiveData<HealthSurveyData> get() = _surveyData

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

    // 檢查是否所有必填欄位都已填寫
    fun isSurveyComplete(): Boolean {
        val data = _surveyData.value
        return data?.name?.isNotEmpty() == true &&
                data.gender?.isNotEmpty() == true &&  //
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
                data.region?.isNotEmpty() == true
    }

    // 清除所有問卷答案
    fun clearSurvey() {
        _surveyData.value = HealthSurveyData() // 重置為一個空的問卷數據
    }
}
