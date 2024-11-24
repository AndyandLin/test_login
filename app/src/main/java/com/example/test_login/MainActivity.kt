// MainActivity.kt
package com.example.test_login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.test_login.network.api.ApiService
import com.example.test_login.network.api.LoginRequest
import com.example.test_login.network.api.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var memberNumEdit: EditText
    private lateinit var pwdEdit: EditText

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/api/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeApiService()
        initializeViews()
        setupClickListeners()
    }

    private fun initializeApiService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    private fun initializeViews() {
        memberNumEdit = findViewById<EditText>(R.id.member_Num_edit)
        pwdEdit = findViewById<EditText>(R.id.pwd_edit).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private fun setupClickListeners() {
        // 登入按鈕
        findViewById<Button>(R.id.login_button).setOnClickListener {
            handleLoginClick()
        }

        // 忘記密碼按鈕
        findViewById<Button>(R.id.forget_PWD).setOnClickListener {
            startActivity(Intent(this, ForgetPWDPageStep1::class.java))
        }

        // 新增權限按鈕
        findViewById<Button>(R.id.add_new_power).setOnClickListener {
            startActivity(Intent(this, addnewpower_page::class.java))
        }
    }

    private fun handleLoginClick() {
        val username = memberNumEdit.text.toString()
        val password = pwdEdit.text.toString()

        if (validateInput(username, password)) {
            lifecycleScope.launch {
                login(username, password)
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        return when {
            username.isEmpty() || password.isEmpty() -> {
                showToast("請輸入帳號和密碼")
                false
            }
            else -> true
        }
    }

    private suspend fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        try {
            val response = apiService.loginUser(loginRequest)
            handleLoginResponse(response)
        } catch (e: Exception) {
            showToast("登入失敗，請稍後再試: ${e.message}")
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful) {
            navigateToHomePage()
        } else {
            showAlert("登入失敗", "員工編號或密碼錯誤")
        }
    }

    private fun navigateToHomePage() {
        startActivity(Intent(this, Home_page_Act::class.java))
        finish()
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("確定") { _, _ ->
                clearInputFields()
            }
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearInputFields() {
        memberNumEdit.text.clear()
        pwdEdit.text.clear()
    }
}