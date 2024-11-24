package com.example.test_login.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.test_login.data.dao.BrainHealthDao
import com.example.test_login.data.model.BrainHealthEntity
import com.example.test_login.data.dao.TaskDao
import com.example.test_login.data.model.TaskEntity

@Database(entities = [BrainHealthEntity::class, TaskEntity::class], version = 2) // 增加版本號
abstract class AppDatabase : RoomDatabase() {
    abstract fun brainHealthDao(): BrainHealthDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE tasks ADD COLUMN isSynced INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2) // 添加遷移
                    .build().also { INSTANCE = it }
            }
        }
    }
}