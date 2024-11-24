package com.example.test_login.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.test_login.data.model.BrainHealthEntity

@Dao
interface BrainHealthDao {
    @Insert
    suspend fun insert(brainHealth: BrainHealthEntity)

    @Query("SELECT * FROM brain_health")
    suspend fun getAll(): List<BrainHealthEntity>

    @Delete
    suspend fun delete(brainHealth: BrainHealthEntity) // 刪除特定的實例

    @Query("DELETE FROM brain_health")
    suspend fun deleteAll() // 刪除所有資料
}