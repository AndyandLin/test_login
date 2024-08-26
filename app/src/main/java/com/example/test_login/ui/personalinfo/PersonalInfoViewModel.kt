package com.example.test_login.ui.personalinfo

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_login.util.DateUtils
import java.util.Date

class PersonalInfoViewModel(private val context: Context) : ViewModel() {

    // SharedPreferences 实例
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("personal_info_prefs", Context.MODE_PRIVATE)

    // LiveData 用于观察个人信息数据
    private val _personalInfo = MutableLiveData<PersonalInfo>().apply {
        // 从 SharedPreferences 加载初始数据
        value = loadPersonalInfo()
    }
    val personalInfo: LiveData<PersonalInfo> = _personalInfo

    /**
     * 保存个人信息，并将其更新到 LiveData 中
     * @param newPersonalInfo 新的个人信息
     */
    fun savePersonalInfo(newPersonalInfo: PersonalInfo) {
        _personalInfo.value = newPersonalInfo // 更新 LiveData
        saveToDataSource(newPersonalInfo) // 保存数据到 SharedPreferences
    }

    /**
     * 从 SharedPreferences 加载个人信息
     * @return 个人信息数据
     */
    private fun loadPersonalInfo(): PersonalInfo {
        return PersonalInfo(
            name = sharedPreferences.getString("name", "") ?: "",
            idNumber = sharedPreferences.getString("idNumber", "") ?: "",
            phone = sharedPreferences.getString("phone", "") ?: "",
            email = sharedPreferences.getString("email", "") ?: "",
            username = sharedPreferences.getString("username", "") ?: "",
            organization = sharedPreferences.getString("organization", "") ?: "",
            birthDate = DateUtils.parseDate(sharedPreferences.getString("birthDate", "") ?: "")
        )
    }

    /**
     * 保存个人信息到 SharedPreferences
     * @param personalInfo 需要保存的个人信息
     */
    private fun saveToDataSource(personalInfo: PersonalInfo) {
        with(sharedPreferences.edit()) {
            putString("name", personalInfo.name)
            putString("idNumber", personalInfo.idNumber)
            putString("phone", personalInfo.phone)
            putString("email", personalInfo.email)
            putString("username", personalInfo.username)
            putString("organization", personalInfo.organization)
            putString(
                "birthDate",
                personalInfo.birthDate?.let { DateUtils.formatDate(it) } ?: "" // Handle null value
            )
            apply() // 保存更改
        }
    }
}
