package com.example.test_login.ui.healthsurvey

// 定義一個數據類來封裝問卷的所有字段
data class HealthSurveyData(
    var name: String? = null,                     // 姓名
    var gender: String? = null,
    var birthdate: String? = null,                // 出生年月日
    var phone: String? = null,                    // 聯絡電話
    var address: String? = null,                  // 通訊地址
    var maritalStatus: String? = null,            // 婚姻狀態
    var educationLevel: String? = null,           // 教育程度
    var economicStatus: String? = null,           // 經濟狀況
    var caseDate: String? = null,                 // 收案日期
    var livingWithFamily: String? = null,         // 與家人同住
    var jobStatus: String? = null,                // 工作狀態
    var religion: String? = null,                 // 宗教信仰
    var region: String? = null                    // 收案地區
)
