package com.example.test_login.data.dao

import androidx.room.*
import com.example.test_login.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity): Long

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM tasks WHERE is_synced = 0") // 確保列名與 TaskEntity 中一致
    suspend fun getUnsyncedTasks(): List<TaskEntity>

    @Query("UPDATE tasks SET is_synced = :isSynced WHERE id = :taskId") // 確保列名與 TaskEntity 中一致
    suspend fun updateTaskSyncStatus(taskId: Long, isSynced: Boolean) // 將 taskId 類型更改為 Long

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>
}