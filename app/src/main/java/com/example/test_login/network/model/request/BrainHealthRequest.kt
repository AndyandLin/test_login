package com.example.test_login.network.model.response

import com.google.gson.annotations.SerializedName

data class BrainHealthRequest(
    @SerializedName("Name") val name: String,
    @SerializedName("Gender") val gender: String,
    @SerializedName("Birth") val birth: String,
    @SerializedName("CorrespondenceAddress") val correspondenceAddress: String? = "-",
    @SerializedName("ContactNumber") val contactNumber: String,
    @SerializedName("Education") val education: String,
    @SerializedName("MaritalStatus") val maritalStatus: String,
    @SerializedName("EconomicSituation") val economicSituation: String,
    @SerializedName("CaseDate") val caseDate: String,
    @SerializedName("LiveTogether") val liveTogether: Boolean,
    @SerializedName("Work") val work: Int,
    @SerializedName("Religion") val religion: Int,
    @SerializedName("Area") val area: Int,
    @SerializedName("Questions") val questions: List<Boolean>
) {
    // 驗證方法
    fun validate(): Boolean {
        return name.isNotBlank() &&
                gender.isNotBlank() &&
                birth.isNotBlank() &&
                contactNumber.isNotBlank() &&
                education.isNotBlank() &&
                maritalStatus.isNotBlank() &&
                economicSituation.isNotBlank() &&
                caseDate.isNotBlank() &&
                questions.size == 15
    }
}