package com.example.test_login.utils

import android.content.Context
import android.util.Log
import com.example.test_login.data.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object DatabaseUtils {
    private const val TAG = "DatabaseCheck"

    fun checkDatabase(context: Context) {
        // 檢查數據庫文件
        val dbFile = context.getDatabasePath("app_database")
        Log.d(TAG, "數據庫路徑: ${dbFile.absolutePath}")

        if (dbFile.exists()) {
            Log.d(TAG, "數據庫文件存在")
            Log.d(TAG, "文件大小: ${dbFile.length()} bytes")

            // 在協程中執行數據庫操作
            CoroutineScope(Dispatchers.IO).launch {
                val database = AppDatabase.getInstance(context)
                database.taskDao().getAllTasks().collect { tasks -> // 使用 collect 收集 Flow
                    Log.d(TAG, "總任務數: ${tasks.size}")
                    tasks.forEach { task ->
                        Log.d(TAG, """
                            任務ID: ${task.id}
                            標題: ${task.title}
                            描述: ${task.description}
                            同步狀態: ${if (task.isSynced) "已同步" else "未同步"}
                            創建時間: ${task.createdAt}
                            更新時間: ${task.updatedAt}
                            ----------------------
                        """.trimIndent())
                    }
                }
            }
        } else {
            Log.e(TAG, "數據庫文件不存在！")
        }
    }

    fun clearDatabase(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val database = AppDatabase.getInstance(context)
                database.clearAllTables()
                Log.d(TAG, "數據庫已清空")
            } catch (e: Exception) {
                Log.e(TAG, "清空數據庫失敗: ${e.message}")
            }
        }
    }
}