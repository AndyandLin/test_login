package com.example.test_login.network.model.response

data class BrainHealthResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)