package com.example.test_login.ui.personalinfo

import java.util.Date

data class PersonalInfo(
    val name: String = "",
    val idNumber: String = "",
    val phone: String = "",
    val email: String = "",
    val username: String = "",
    val organization: String = "",
    val birthDate: Date? = null
)
