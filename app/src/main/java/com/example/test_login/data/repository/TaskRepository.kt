// TaskRepository.kt
package com.example.test_login.data.repository

import android.util.Log
import com.example.test_login.network.api.ApiService
import com.example.test_login.data.dao.TaskDao
import com.example.test_login.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val apiService: ApiService
) {
    // 獲取所有任務
    fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    // 新增任務
    suspend fun insertTask(taskEntity: TaskEntity) {
        try {
            // 先儲存到本地資料庫
            val localId = taskDao.insertTask(taskEntity)

            // 嘗試同步到伺服器
            try {
                val response = apiService.createTask(taskEntity)
                if (response.isSuccessful && response.body() != null) {
                    taskDao.updateTaskSyncStatus(localId, true) // 直接使用 localId
                }
            } catch (e: Exception) {
                Log.e("TaskRepository", "同步到伺服器失敗: ${e.message}")
            }
        } catch (e: Exception) {
            Log.e("TaskRepository", "儲存任務失敗: ${e.message}")
            throw e
        }
    }

    // 同步未同步的任務
    suspend fun syncPendingTasks() {
        try {
            val unsyncedTasks = taskDao.getUnsyncedTasks()
            if (unsyncedTasks.isEmpty()) {
                return
            }

            val response = apiService.syncTasks(unsyncedTasks)
            if (response.isSuccessful) {
                response.body()?.let { syncedTasks ->
                    syncedTasks.forEach { syncedTask ->
                        taskDao.updateTaskSyncStatus(syncedTask.id, true)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TaskRepository", "批量同步失敗: ${e.message}")
        }
    }

    // 更新任務
    suspend fun updateTask(taskEntity: TaskEntity) {
        try {
            taskDao.updateTask(taskEntity.copy(isSynced = false))
            syncPendingTasks() // 修改這裡的方法名稱
        } catch (e: Exception) {
            Log.e("TaskRepository", "更新任務失敗: ${e.message}")
            throw e
        }
    }

    // 刪除任務
    suspend fun deleteTask(taskEntity: TaskEntity) {
        try {
            taskDao.deleteTask(taskEntity)
        } catch (e: Exception) {
            Log.e("TaskRepository", "刪除任務失敗: ${e.message}")
            throw e
        }
    }

    // 檢查是否有未同步的任務
    suspend fun hasUnsyncedTasks(): Boolean {
        return taskDao.getUnsyncedTasks().isNotEmpty()
    }
}