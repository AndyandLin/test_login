package com.example.test_login.data.base

abstract class BaseFormEntity {
    abstract val id: Long
    abstract val isSynced: Boolean
    abstract val createdAt: Long
    abstract val updatedAt: Long
}