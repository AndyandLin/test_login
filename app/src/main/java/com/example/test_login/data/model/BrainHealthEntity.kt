package com.example.test_login.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brain_health")
data class BrainHealthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val gender: String,
    val birthdate: String,
    val phone: String,
    val address: String,
    val maritalStatus: String,
    val educationLevel: String,
    val economicStatus: String,
    val caseDate: String,
    val livingWithFamily: String?,
    val jobStatus: String?,
    val religion: String?,
    val region: String?,
    val questions: String  // JSON 格式存儲問題答案
)