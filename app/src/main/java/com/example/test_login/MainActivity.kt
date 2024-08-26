package com.example.test_login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Login_btn = findViewById<Button>(R.id.login_button)
        val forgetpwd = findViewById<Button>(R.id.forget_PWD)
        val addnewpower = findViewById<Button>(R.id.add_new_power)

        Login_btn.setOnClickListener{
            // 登錄按鈕被點擊時執行以下代碼
            // 在這裡添加驗證登錄信息的邏輯，例如檢查用戶名和密碼是否正確
            // 取得 EditText 輸入框中的 會員編碼與密碼並設置為密碼輸入
            val Member_num_edit = findViewById<EditText>(R.id.member_Num_edit)
            val PWD = findViewById<EditText>(R.id.pwd_edit)
            PWD.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            val username = Member_num_edit.text.toString()
            val password = PWD.text.toString()
            // 檢查帳號和密碼是否正確
            if (username == "test_ntunhs" && password == "project12") {
                // 如果驗證成功，則跳轉到下一個活動（例如 Home_page_Act）
                val intent = Intent(this, Home_page_Act::class.java)
                startActivity(intent)
            } else {
                // 如果帳號或密碼錯誤，顯示錯誤對話框
                val builder = AlertDialog.Builder(this)
                builder.setTitle("登入失敗")
                builder.setMessage("員工編號或密碼錯誤")
                builder.setPositiveButton("確定") { dialog, which ->
                    // 清除輸入框中的內容
                    Member_num_edit.text.clear()
                    PWD.text.clear()
                }
                builder.show()
            }
        }

        forgetpwd.setOnClickListener{
            // 忘記密碼按鈕被點擊時執行以下代碼
            val intent = Intent(this, forget_PWD_page::class.java)
            startActivity(intent)
        }

        addnewpower.setOnClickListener{
            // 新增權限被點擊時執行以下代碼
            val intent = Intent(this, addnewpower_page::class.java)
            startActivity(intent)
        }
    }
}