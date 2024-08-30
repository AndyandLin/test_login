package com.example.test_login.ui.personalinfo

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_login.util.DateUtils
import java.util.Date

// PersonalInfoViewModel 繼承自 ViewModel，用來管理個人資訊數據
class PersonalInfoViewModel(private val context: Context) : ViewModel() {

    // 建立 SharedPreferences 實例，用於保存和讀取資料
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("personal_info_prefs", Context.MODE_PRIVATE)

    // LiveData 用於觀察和管理個人資訊數據
    private val _personalInfo = MutableLiveData<PersonalInfo>().apply {
        // 初始化時，從 SharedPreferences 加載已保存的個人資訊
        value = loadPersonalInfo()
    }
    val personalInfo: LiveData<PersonalInfo> = _personalInfo

    /**
     * 保存個人資訊，並更新 LiveData
     * @param newPersonalInfo 新的個人資訊
     */
    fun savePersonalInfo(newPersonalInfo: PersonalInfo) {
        _personalInfo.value = newPersonalInfo // 更新 LiveData，通知 UI 進行更新
        saveToDataSource(newPersonalInfo) // 將數據保存到 SharedPreferences 中
    }

    /**
     * 從 SharedPreferences 加載個人資訊
     * @return 個人資訊數據
     */
    private fun loadPersonalInfo(): PersonalInfo {
        return PersonalInfo(
            name = sharedPreferences.getString("name", "北護專題測試") ?: "",
            idNumber = sharedPreferences.getString("idNumber", "") ?: "",
            phone = sharedPreferences.getString("phone", "") ?: "",
            email = sharedPreferences.getString("email", "") ?: "",
            username = sharedPreferences.getString("username", "") ?: "",
            organization = sharedPreferences.getString("organization", "") ?: "",
            // 將儲存的生日字串解析為 Date 物件
            birthDate = DateUtils.parseDate(sharedPreferences.getString("birthDate", "") ?: "")
        )
    }

    /**
     * 將個人資訊保存到 SharedPreferences
     * @param personalInfo 需要保存的個人資訊
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
                personalInfo.birthDate?.let { DateUtils.formatDate(it) } ?: "" // 如果生日是 null，儲存為空字串
            )
            apply() // 提交更改並保存
        }
    }
}
