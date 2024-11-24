package com.example.test_login.data.base

interface BaseDao<T> {
    suspend fun insert(item: T): Long
    suspend fun update(item: T)
    suspend fun delete(item: T)
    suspend fun getUnsyncedItems(): List<T>
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}