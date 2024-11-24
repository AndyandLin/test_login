package com.example.test_login.ui.BrainHealth

import com.google.gson.annotations.SerializedName

data class BrainHealthData(
    @SerializedName("Name") var name: String? = null,
    @SerializedName("Gender") var gender: String? = null,
    @SerializedName("Birth") var birthdate: String? = null,
    @SerializedName("ContactNumber") var phone: String? = null,
    @SerializedName("CorrespondenceAddress") var address: String? = null,
    @SerializedName("MaritalStatus") var maritalStatus: String? = null,
    @SerializedName("Education") var educationLevel: String? = null,
    @SerializedName("EconomicSituation") var economicStatus: String? = null,
    @SerializedName("CaseDate") var caseDate: String? = null,
    @SerializedName("LiveTogether") var livingWithFamily: String? = null,
    @SerializedName("Work") var jobStatus: String? = null,
    @SerializedName("Religion") var religion: String? = null,
    @SerializedName("Area") var region: String? = null,
    @SerializedName("Q1") var q1: Boolean = false,
    @SerializedName("Q2") var q2: Boolean = false,
    @SerializedName("Q3") var q3: Boolean = false,
    @SerializedName("Q4") var q4: Boolean = false,
    @SerializedName("Q5") var q5: Boolean = false,
    @SerializedName("Q6") var q6: Boolean = false,
    @SerializedName("Q7") var q7: Boolean = false,
    @SerializedName("Q8") var q8: Boolean = false,
    @SerializedName("Q9") var q9: Boolean = false,
    @SerializedName("Q10") var q10: Boolean = false,
    @SerializedName("Q11") var q11: Boolean = false,
    @SerializedName("Q12") var q12: Boolean = false,
    @SerializedName("Q13") var q13: Boolean = false,
    @SerializedName("Q14") var q14: Boolean = false,
    @SerializedName("Q15") var q15: Boolean = false
) {
    // 添加轉換方法
    fun toMap(): Map<String, String> {
        return mapOf(
            "姓名" to (name ?: "未知姓名"),
            "性別" to (gender ?: "未知性別"),
            "出生年月日" to (birthdate ?: "未知生日"),
            "聯絡電話" to (phone ?: "未知電話"),
            "通訊地址" to (address ?: "未知地址"),
            "婚姻狀態" to (maritalStatus ?: "未知婚姻狀態"),
            "教育程度" to (educationLevel ?: "未知教育程度"),
            "經濟狀況" to (economicStatus ?: "未知經濟狀況"),
            "收案日期" to (caseDate ?: "未知日期"),
            "與家人同住" to (livingWithFamily ?: "未知"),
            "工作" to (jobStatus ?: "未知"),
            "宗教信仰" to (religion ?: "未知"),
            "收案地區" to (region ?: "未知地區"),
            "1. 我到人多的地方，無法應付那種壓力很大的感覺" to q1.toString(),
            "2. 我覺得我無法親近別人" to q2.toString(),
            "3. 我覺得我無法信任別人" to q3.toString(),
            "4. 我覺得我無法跟別人說話" to q4.toString(),
            "5. 我覺得我無法跟別人相處" to q5.toString(),
            "6. 我覺得我無法跟別人合作" to q6.toString(),
            "7. 我覺得我無法跟別人一起工作" to q7.toString(),
            "8. 我覺得我無法跟別人一起生活" to q8.toString(),
            "9. 我覺得我無法跟別人一起玩" to q9.toString(),
            "10. 我覺得我無法跟別人一起學習" to q10.toString(),
            "11. 我覺得我無法跟別人一起運動" to q11.toString(),
            "12. 我覺得我無法跟別人一起吃飯" to q12.toString(),
            "13. 我覺得我無法跟別人一起看電影" to q13.toString(),
            "14. 我覺得我無法跟別人一起聊天" to q14.toString(),
            "15. 我覺得我無法跟別人一起散步" to q15.toString()
        )
    }

    // 添加驗證方法
    fun validate(): Boolean {
        return !name.isNullOrBlank() &&
                !gender.isNullOrBlank() &&
                !birthdate.isNullOrBlank() &&
                !phone.isNullOrBlank() &&
                !address.isNullOrBlank() &&
                !maritalStatus.isNullOrBlank() &&
                !educationLevel.isNullOrBlank() &&
                !economicStatus.isNullOrBlank() &&
                !caseDate.isNullOrBlank() &&
                !livingWithFamily.isNullOrBlank() &&
                !jobStatus.isNullOrBlank() &&
                !religion.isNullOrBlank() &&
                !region.isNullOrBlank()
    }
}