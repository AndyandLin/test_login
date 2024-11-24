package com.example.test_login.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.test_login.data.model.TaskEntity
import com.example.test_login.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // 獲取所有任務的 LiveData
    val allTasks = repository.getAllTasks().asLiveData()

    // 新增任務
    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val taskEntity = TaskEntity(
                title = title,
                description = description
            )
            repository.insertTask(taskEntity)
        }
    }

    // 更新任務
    fun updateTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(taskEntity)
        }
    }

    // 刪除任務
    fun deleteTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(taskEntity)
        }
    }

    // 手動同步任務
    fun syncTasks() {
        viewModelScope.launch {
            repository.syncPendingTasks()  // 修改這裡以匹配 Repository 中的方法名
        }
    }

    // 檢查是否有未同步的任務
    fun checkUnsyncedTasks() {
        viewModelScope.launch {
            val hasUnsynced = repository.hasUnsyncedTasks()
            // 可以通過 LiveData 通知 UI
        }
    }
}