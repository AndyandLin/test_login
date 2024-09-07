package com.example.test_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class addnewpower_page : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private var isChecked: Boolean = false
    private var isUserIdValid: Boolean = false
    private val CHECK_SYMBOL = "✔️"
    private val CROSS_SYMBOL = "❌"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnewpower_page)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val back_login_btn2 = findViewById<Button>(R.id.back_login_btn)
        val singup_btn = findViewById<Button>(R.id.singup_btn)
        val chk_button2 = findViewById<Button>(R.id.chk_button2)
        val member_Num_edit3 = findViewById<EditText>(R.id.member_Num_edit3)
        val email_edit2 = findViewById<EditText>(R.id.email_edit2)
        val pwd_chk_edit = findViewById<EditText>(R.id.pwd_chk_edit)
        val pwd_edit2 = findViewById<EditText>(R.id.pwd_edit2)
        val hospitalCodeEdit = findViewById<EditText>(R.id.Hospital_Code)

        back_login_btn2.setOnClickListener {
            finish()
        }

        chk_button2.setOnClickListener {
            val memberNumber = member_Num_edit3.text.toString()
            if (memberNumber.isEmpty()) {
                Toast.makeText(this, "請輸入使用者編號", Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.chk_button2).text = CROSS_SYMBOL
                isChecked = false
                isUserIdValid = false
            } else {
                checkUserId(memberNumber)
            }
        }

        singup_btn.setOnClickListener {
            val memberNumber = member_Num_edit3.text.toString()
            val email = email_edit2.text.toString()
            val password = pwd_edit2.text.toString()
            val confirmPassword = pwd_chk_edit.text.toString()
            val hospitalCode = hospitalCodeEdit.text.toString()

            if (memberNumber.isNotEmpty() && email.isNotEmpty() &&
                password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                hospitalCode.isNotEmpty() && isChecked && isUserIdValid
            ) {
                if (password == confirmPassword) {
                    registerUser(memberNumber, email, password, hospitalCode)
                } else {
                    Toast.makeText(this, "密碼與確認密碼不相同", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "請輸入完整的註冊資訊並完成檢查", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserId(userId: String) {
        Log.d("API_CALL", "Checking user ID: $userId")
        apiService.checkUserId(userId).enqueue(object : Callback<CheckUserIdResponse> {
            override fun onResponse(call: Call<CheckUserIdResponse>, response: Response<CheckUserIdResponse>) {
                Log.d("API_RESPONSE", "Response code: ${response.code()}")
                Log.d("API_RESPONSE", "Response body: ${response.body()}")
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.isAvailable == true) {
                        Toast.makeText(this@addnewpower_page, "使用者編號可用", Toast.LENGTH_SHORT).show()
                        isChecked = true
                        isUserIdValid = true
                        findViewById<Button>(R.id.chk_button2).text = CHECK_SYMBOL
                    } else {
                        Toast.makeText(this@addnewpower_page, "使用者編號已有人使用", Toast.LENGTH_SHORT).show()
                        isChecked = false
                        isUserIdValid = false
                        findViewById<Button>(R.id.chk_button2).text = CROSS_SYMBOL
                    }
                } else {
                    Log.e("API_ERROR", "Error response: ${response.errorBody()?.string()}")
                    Toast.makeText(this@addnewpower_page, "檢查失敗：${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CheckUserIdResponse>, t: Throwable) {
                Log.e("API_ERROR", "Network error: ${t.message}", t)
                Toast.makeText(this@addnewpower_page, "網絡錯誤：${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerUser(userId: String, email: String, password: String, hospitalCode: String) {
        apiService.registerUser(userId, email, password, hospitalCode).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.status == "success") {
                        Toast.makeText(this@addnewpower_page, "註冊成功", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@addnewpower_page, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@addnewpower_page, "註冊失敗：${result?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@addnewpower_page, "註冊失敗：${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@addnewpower_page, "網絡錯誤：${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    var lastTime: Long = 0
    override fun finish() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > 3 * 1000) {
            lastTime = currentTime
            Toast.makeText(this, "再按一下離開", Toast.LENGTH_LONG).show()
        } else {
            super.finish()
        }
    }
}