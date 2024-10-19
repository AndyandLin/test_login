package com.example.test_login

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityHomePageBinding
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home_page_Act : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 獲取工具欄並設置為活動的操作欄
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 獲取底部導航視圖
        val navView: BottomNavigationView = findViewById(R.id.nav_View)

        // 獲取導航控制器
        val navController = findNavController(R.id.nav_host_fragment_activity_home_page)

        // 將每個菜單 ID 作為一組 ID 傳遞，因為每個菜單應視為頂級目的地
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_edit_file,
                R.id.navigation_personal_info,
                R.id.navigation_case_manegement
            )
        )

        // 設置導航控制器與操作欄的關係
        setupActionBarWithNavController(navController, appBarConfiguration)
        // 將底部導航視圖與導航控制器關聯
        navView.setupWithNavController(navController)
    }
}