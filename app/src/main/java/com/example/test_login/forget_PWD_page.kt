package com.example.test_login

import android.content.Intent
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.random.Random
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class forget_PWD_page : AppCompatActivity() {

    private lateinit var correctVerificationCode: String // 宣告正確的驗證碼(延遲初始化)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd_page)

        // 初始化元件
        val back_login_btn = findViewById<Button>(R.id.back_login_btn)
        val send_btn = findViewById<Button>(R.id.send_btn)
        val chk_button = findViewById<Button>(R.id.chk_button)
        val member_Num_edit2 = findViewById<EditText>(R.id.member_Num_edit2)
        val email_edit = findViewById<EditText>(R.id.email_edit)
        val captcha_edit = findViewById<EditText>(R.id.captcha_edit)

        // 檢查按鈕點擊事件
        chk_button.setOnClickListener {
            val email = email_edit.text.toString()
            val memberNumber = member_Num_edit2.text.toString()
            // 檢查會員編碼和電子郵件是否正確
            if (memberNumber == "test_ntunhs" && email == "testntunhs@gmail.com") {
                // 正確，根據邏輯生成驗證碼並顯示
                correctVerificationCode = generateVerificationCode(6) // 生成 6 位數的隨機驗證碼
                Toast.makeText(this, "驗證碼：$correctVerificationCode", Toast.LENGTH_LONG).show()
            } else {
                // 錯誤，顯示錯誤訊息
                Toast.makeText(this, "驗證失敗，請確認會員編碼和電子郵件是否正確", Toast.LENGTH_SHORT).show()
            }
        }

        // 送出按鈕點擊事件
        send_btn.setOnClickListener {
            val enteredVerificationCode = captcha_edit.text.toString()
            if (enteredVerificationCode == correctVerificationCode) {
                // 驗證碼正確，可以執行送出操作，例如重置密碼等
                // 顯示密碼對話框
                showPasswordDialog()
            } else {
                // 驗證碼錯誤，顯示錯誤訊息
                Toast.makeText(this, "驗證碼錯誤", Toast.LENGTH_SHORT).show()
            }
        }

        back_login_btn.setOnClickListener{
            finish() // 結束當前 Activity
        }
    }

    // 生成指定長度的隨機驗證碼
    private fun generateVerificationCode(length: Int): String {
        // 定義字符池，包含數字 0 到 9、小寫字母 a 到 z
        val charPool: List<Char> = ('0'..'9') + ('a'..'z')

        // 使用 Kotlin 的隨機函數生成隨機驗證碼
        return (1..length)
            .map { Random.nextInt(0, charPool.size) } // 隨機選擇字符池中的索引
            .map(charPool::get)                            // 獲取對應索引的字符
            .joinToString("")                     // 將字符組合成字符串
    }

    // 顯示密碼對話框
    private fun showPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("找回密碼")
        builder.setMessage("您的原始密碼是：project12")
        builder.setPositiveButton("確定") { dialog, which ->
            // 點擊確定後的處理
            // 可以在這裡執行相應的操作，例如返回主頁面
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        builder.show()
    }

    // 儲存上一次按返回鍵的時間，以實現雙擊返回退出的功能
    var lastTime : Long = 0
    override fun finish(){
        val currentTime = System.currentTimeMillis()
        if(currentTime - lastTime > 3 * 1000){ // 如果距離上一次按返回鍵超過3秒
            lastTime = currentTime
            Toast.makeText(this,"再按一下離開", Toast.LENGTH_LONG).show()
        } else{ // 如果在3秒內再次按返回鍵，則執行父類的 finish() 方法，退出 Activity
            super.finish()
        }
    }
}