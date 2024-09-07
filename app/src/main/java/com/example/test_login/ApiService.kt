package com.example.test_login

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("api/check_user_id/")
    @FormUrlEncoded
    fun checkUserId(@Field("userId") userId: String): Call<CheckUserIdResponse>

    @POST("api/register/")
    @FormUrlEncoded
    fun registerUser(
        @Field("userId") userId: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("hospitalCode") hospitalCode: String
    ): Call<RegisterResponse>

    @POST("api/login/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}

data class CheckUserIdResponse(
    val isAvailable: Boolean
)

data class RegisterResponse(
    val status: String,
    val message: String
)

data class LoginResponse(
    val status: String,
    val message: String?
)