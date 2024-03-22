package com.example.test_login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class addnewpower_page : AppCompatActivity() {

    private var isChecked: Boolean = false // 檢查按鈕的狀態
    private var isUserIdValid: Boolean = false // 使用者編號是否有效
    private val CHECK_SYMBOL = "✔️" // 打勾符號
    private val CROSS_SYMBOL = "❌" // 叉叉符號

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnewpower_page)
        // 初始化元件
        val back_login_btn2 = findViewById<Button>(R.id.back_login_btn)
        val singup_btn = findViewById<Button>(R.id.singup_btn)
        val chk_button2 = findViewById<Button>(R.id.chk_button2)
        val member_Num_edit3 = findViewById<EditText>(R.id.member_Num_edit3)
        val email_edit2 = findViewById<EditText>(R.id.email_edit2)
        val pwd_chk_edit = findViewById<EditText>(R.id.pwd_chk_edit)
        val pwd_edit2 = findViewById<EditText>(R.id.pwd_edit2)

        back_login_btn2.setOnClickListener{
            // 返回登入頁面
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 檢查按鈕點擊事件
        chk_button2.setOnClickListener {
            val memberNumber = member_Num_edit3.text.toString()

            // 檢查會員編碼是否為 test_ntunhs
            if (memberNumber == "test_ntunhs") {
                // 顯示使用者編號已有人使用的提示
                Toast.makeText(this, "使用者編號已有人使用", Toast.LENGTH_SHORT).show()
                isChecked = false // 設定檢查按鈕為錯誤狀態
                chk_button2.text = CROSS_SYMBOL // 設定叉叉符號
            } else {
                // 顯示使用者編號驗證完成的提示
                Toast.makeText(this, "使用者編號驗證完成", Toast.LENGTH_SHORT).show()
                isChecked = true // 設定檢查按鈕為成功狀態
                isUserIdValid = true // 使用者編號有效
                chk_button2.text = CHECK_SYMBOL // 設定打勾符號
            }
        }

        // 註冊按鈕點擊事件
        singup_btn.setOnClickListener {
            val memberNumber = member_Num_edit3.text.toString()
            val email = email_edit2.text.toString()
            val password = pwd_edit2.text.toString()
            val confirmPassword = pwd_chk_edit.text.toString()

            // 檢查所有欄位是否都已輸入內容並且檢查按鈕為成功狀態且使用者編號有效
            if (memberNumber.isNotEmpty() && email.isNotEmpty() &&
                password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                isChecked && isUserIdValid) {

                // 檢查密碼與確認密碼是否相同
                if (password == confirmPassword) {
                    // 顯示註冊權限成功的提示
                    Toast.makeText(this, "註冊權限成功", Toast.LENGTH_SHORT).show()

                    // 跳轉回 MainActivity 畫面
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // 顯示密碼與確認密碼不相同的提示
                    Toast.makeText(this, "密碼與確認密碼不相同", Toast.LENGTH_SHORT).show()
                }
            } else {
                // 顯示輸入欄位未完整或使用者編號無效的提示
                Toast.makeText(this, "請輸入完整的註冊資訊並完成檢查", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
