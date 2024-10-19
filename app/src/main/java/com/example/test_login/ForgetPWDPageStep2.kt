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

class ForgetPWDPageStep2 : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd_page_step2)

        val memberNumber = intent.getStringExtra("memberNumber") ?: return

        val chkButton = findViewById<Button>(R.id.chk_button)
        val captchaEdit = findViewById<EditText>(R.id.captcha_edit)
        val newPasswordEdit = findViewById<EditText>(R.id.new_password_edit)
        val back_login_btn = findViewById<Button>(R.id.back_login_btn) // 返回登入頁面按鈕

        // 確認按鈕的點擊事件
        chkButton.setOnClickListener {
            val verificationCode = captchaEdit.text.toString()
            val newPassword = newPasswordEdit.text.toString()
            verifyCodeAndResetPassword(memberNumber, verificationCode, newPassword)
        }

        // 設置返回登錄頁面按鈕的點擊事件
        back_login_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // 返回到主頁面 (登入頁面)
            startActivity(intent) // 啟動意圖 (跳轉頁面)
        }

    }

    private fun verifyCodeAndResetPassword(memberNumber: String, verificationCode: String, newPassword: String) {
        val json = JSONObject()
        json.put("userId", memberNumber)
        json.put("verificationCode", verificationCode)
        json.put("newPassword", newPassword)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/verify_code_and_reset_password/")
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    runOnUiThread {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ForgetPWDPageStep2, "密碼重置成功", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ForgetPWDPageStep2, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            val errorMessage = try {
                                JSONObject(responseBody ?: "").optString("error", "未知錯誤")
                            } catch (e: Exception) {
                                "密碼重置失敗: ${response.code}"
                            }
                            Toast.makeText(this@ForgetPWDPageStep2, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@ForgetPWDPageStep2, "網絡錯誤：${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
