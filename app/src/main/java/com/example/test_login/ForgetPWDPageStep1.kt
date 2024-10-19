package com.example.test_login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

// 忘記密碼的第一步頁面，主要功能是檢查使用者編號和信箱是否正確，並發送驗證碼
class ForgetPWDPageStep1 : AppCompatActivity() {

    // OkHttp 用來處理網路請求的客戶端
    private val client = OkHttpClient()

    // 當頁面被創建時觸發
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd_page_step1)

        // 取得 UI 元件 (按鈕與輸入框)
        val sendBtn = findViewById<Button>(R.id.send_btn) // 送出按鈕
        val memberNumEdit = findViewById<EditText>(R.id.member_Num_edit2) // 使用者編號輸入框
        val emailEdit = findViewById<EditText>(R.id.email_edit) // 信箱輸入框
        val back_login_btn = findViewById<Button>(R.id.back_login_btn) // 返回登入頁面按鈕

        // 送出按鈕的點擊事件處理
        sendBtn.setOnClickListener {
            val memberNumber = memberNumEdit.text.toString() // 取得使用者編號
            val email = emailEdit.text.toString() // 取得信箱
            checkUserAndSendVerificationCode(memberNumber, email) // 驗證使用者並發送驗證碼
        }

        // 設置返回登錄頁面按鈕的點擊事件
        back_login_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // 返回到主頁面 (登入頁面)
            startActivity(intent) // 啟動意圖 (跳轉頁面)
        }

    }

    // 檢查使用者資料並發送驗證碼
    private fun checkUserAndSendVerificationCode(memberNumber: String, email: String) {
        // 建立 JSON 資料格式的請求內容
        val json = JSONObject()
        json.put("userId", memberNumber) // 將使用者編號放入 JSON
        json.put("email", email) // 將信箱放入 JSON

        // 將 JSON 轉換為 OkHttp 可以發送的請求體 (RequestBody)
        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        // 建立一個 POST 請求，指定 URL 和請求體
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/check_user_and_send_code/") // 本地開發伺服器 URL
            .post(requestBody) // 設置 POST 方法並傳送請求體
            .build()

        // 使用協程發送網路請求，因為網路請求是耗時操作，需要放在非主線程
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // 執行網路請求
                client.newCall(request).execute().use { response ->
                    // 使用 runOnUiThread 在主線程更新 UI
                    runOnUiThread {
                        if (response.isSuccessful) {
                            // 請求成功，顯示提示訊息並跳轉到下一個頁面
                            Toast.makeText(this@ForgetPWDPageStep1, "員工編號與信箱正確", Toast.LENGTH_SHORT).show()
                            // 跳轉到驗證碼頁面 (第二步)
                            val intent = Intent(this@ForgetPWDPageStep1, ForgetPWDPageStep2::class.java)
                            intent.putExtra("memberNumber", memberNumber) // 傳遞使用者編號到下一頁
                            startActivity(intent) // 開啟新的頁面
                        } else {
                            // 請求失敗，顯示錯誤訊息
                            Toast.makeText(this@ForgetPWDPageStep1, "員工編號或信箱有誤", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: IOException) {
                // 當發生網絡錯誤時，顯示錯誤訊息
                runOnUiThread {
                    Toast.makeText(this@ForgetPWDPageStep1, "網絡錯誤，請稍後再試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
