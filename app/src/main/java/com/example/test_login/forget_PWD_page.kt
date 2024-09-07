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
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class forget_PWD_page : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd_page)

        val back_login_btn = findViewById<Button>(R.id.back_login_btn)
        val send_btn = findViewById<Button>(R.id.send_btn)
        val chk_button = findViewById<Button>(R.id.chk_button)
        val member_Num_edit2 = findViewById<EditText>(R.id.member_Num_edit2)
        val email_edit = findViewById<EditText>(R.id.email_edit)
        val captcha_edit = findViewById<EditText>(R.id.captcha_edit)
        val new_password_edit = findViewById<EditText>(R.id.new_password_edit)

        back_login_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        send_btn.setOnClickListener {
            val memberNumber = member_Num_edit2.text.toString()
            val email = email_edit.text.toString()
            checkUserAndSendVerificationCode(memberNumber, email)
        }

        chk_button.setOnClickListener {
            val memberNumber = member_Num_edit2.text.toString()
            val verificationCode = captcha_edit.text.toString()
            val newPassword = new_password_edit.text.toString()
            verifyCodeAndResetPassword(memberNumber, verificationCode, newPassword)
        }
    }

    private fun checkUserAndSendVerificationCode(memberNumber: String, email: String) {
        val json = JSONObject()
        json.put("userId", memberNumber)
        json.put("email", email)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/check_user_and_send_code/")
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody)

                    runOnUiThread {
                        if (response.isSuccessful) {
                            Toast.makeText(this@forget_PWD_page, "驗證碼已發送", Toast.LENGTH_SHORT).show()
                        } else {
                            val errorMessage = jsonResponse.getString("error")
                            Toast.makeText(this@forget_PWD_page, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@forget_PWD_page, "網絡錯誤，請稍後再試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verifyCodeAndResetPassword(memberNumber: String, verificationCode: String, newPassword: String) {
        val json = JSONObject()
        json.put("userId", memberNumber)
        json.put("verificationCode", verificationCode)
        json.put("newPassword", newPassword)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/verify_code_and_reset_password/")
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    runOnUiThread {
                        if (response.isSuccessful) {
                            try {
                                val jsonResponse = JSONObject(responseBody)
                                Toast.makeText(this@forget_PWD_page, "密碼重置成功", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@forget_PWD_page, MainActivity::class.java)
                                startActivity(intent)
                            } catch (e: JSONException) {
                                Toast.makeText(this@forget_PWD_page, "服務器返回了無效的響應", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@forget_PWD_page, "密碼重置失敗：${response.code}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@forget_PWD_page, "網絡錯誤，請稍後再試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}