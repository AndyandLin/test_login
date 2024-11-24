package com.example.test_login.network.model

import com.example.test_login.network.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/"
    private const val TIMEOUT = 30L // 30秒超時

    // 創建日誌攔截器
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 創建 OkHttpClient
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)  // 添加日誌攔截器
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)  // 連接超時
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)     // 讀取超時
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)    // 寫入超時
        .build()

    // 創建 Retrofit 實例
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // 使用配置好的 client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 創建 API 服務
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}