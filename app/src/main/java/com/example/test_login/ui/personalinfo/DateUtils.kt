package com.example.test_login.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    /**
     * 格式化日期为字符串
     * @param year 年份
     * @param month 月份 (0-11)
     * @param day 日
     * @return 格式化后的日期字符串
     */
    fun formatDate(year: Int, month: Int, day: Int): String {
        return String.format("%04d-%02d-%02d", year, month + 1, day)
    }

    /**
     * 格式化日期对象为字符串
     * @param date 日期对象
     * @return 格式化后的日期字符串
     */
    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    /**
     * 解析日期字符串为日期对象
     * @param dateString 日期字符串
     * @return 日期对象
     */
    fun parseDate(dateString: String): Date? {
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}
