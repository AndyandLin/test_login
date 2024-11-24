package com.example.test_login.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // 使用 Long 作為 ID 類型
    val title: String,
    val description: String,
    @ColumnInfo(name = "is_completed") // 使用 @ColumnInfo 註解來指定列名
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "is_synced") // 使用 @ColumnInfo 註解來指定列名
    val isSynced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    override fun toString(): String {
        return "TaskEntity(id=$id, title='$title', description='$description', isCompleted=$isCompleted, isSynced=$isSynced, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}