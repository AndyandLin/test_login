package com.example.test_login
//網路版本api django
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化 UI 元件
        val Login_btn = findViewById<Button>(R.id.login_button)
        val forgetpwd = findViewById<Button>(R.id.forget_PWD)
        val addnewpower = findViewById<Button>(R.id.add_new_power)

        // 設定登入按鈕的點擊事件
        Login_btn.setOnClickListener {
            // 取得用戶名和密碼輸入框
            val Member_num_edit = findViewById<EditText>(R.id.member_Num_edit)
            val PWD = findViewById<EditText>(R.id.pwd_edit)

            // 設定密碼輸入框的輸入類型為密碼
            PWD.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            // 取得用戶名和密碼
            val username = Member_num_edit.text.toString()
            val password = PWD.text.toString()

            // 驗證用戶名和密碼是否為空
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "請輸入帳號和密碼", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 呼叫 login 函數進行登入操作
            login(username, password)
        }

        // 設定忘記密碼按鈕的點擊事件
        forgetpwd.setOnClickListener {
            val intent = Intent(this, ForgetPWDPageStep1::class.java)
            startActivity(intent)
        }

        // 設定新增權限按鈕的點擊事件
        addnewpower.setOnClickListener {
            val intent = Intent(this, addnewpower_page::class.java)
            startActivity(intent)
        }
    }

    // 執行登入請求的函數
    private fun login(username: String, password: String) {
        // 創建 OkHttpClient 實例
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


        // 建立 JSON 資料
        val json = JSONObject().apply {
            put("username", username)
            put("password", password)
        }

        // 建立 Request Body，設定內容類型為 JSON
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaType(), // 使用擴展函數 toMediaType()
            json.toString()
        )

        // 建立 Request 物件，設定請求方法為 POST，並指定 URL
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/login/login/") // 使用 127.0.0.1 以便在模擬器上連接到主機
            .post(requestBody)
            .build()

        // 執行 HTTP 請求
        client.newCall(request).enqueue(object : Callback {
            // 當請求失敗時執行
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    // 在 UI 線程中顯示錯誤訊息
                    Toast.makeText(this@MainActivity, "登入失敗，請稍後再試: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // 當請求成功時執行
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        // 登入成功，跳轉到 Home_page_Act 活動
                        val intent = Intent(this@MainActivity, Home_page_Act::class.java)
                        startActivity(intent)
                    } else {
                        // 登入失敗，顯示錯誤對話框
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("登入失敗")
                        builder.setMessage("員工編號或密碼錯誤")
                        builder.setPositiveButton("確定") { dialog, _ ->
                            // 清除輸入框中的內容
                            findViewById<EditText>(R.id.member_Num_edit).text.clear()
                            findViewById<EditText>(R.id.pwd_edit).text.clear()
                        }
                        builder.show()
                    }
                }
            }
        })
    }
}
