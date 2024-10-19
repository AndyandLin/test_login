package com.example.test_login.ui.caseManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCaseManagementBinding

class CaseManagementFragment : Fragment() {

    private var _binding: FragmentCaseManagementBinding? = null  // 定義綁定變數
    private val binding get() = _binding!!  // 獲取綁定的視圖

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaseManagementBinding.inflate(inflater, container, false)  // 初始化綁定
        val root: View = binding.root  // 獲取根視圖

        // 範例數據（替換為實際數據）
        val rowData = listOf(
            listOf("王小明", "編輯1", "A123456789", "2024-06-06", "健康問題", "進行中"),
            listOf("陳大同", "編輯2", "B987654321", "2024-06-05", "精神狀態", "進行中")
            // 可以添加更多行
        )

        // 找到 TableLayout
        val tableLayout = binding.TableLayout1

        // 動態添加行到 TableLayout
        for (row in rowData) {
            val tableRow = TableRow(requireContext()).apply {
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.row_background_color)) // 設定行的背景顏色
                setPadding(8, 8, 8, 8)  // 設定內邊距
            }

            for (item in row) {
                val textView = TextView(requireContext()).apply {
                    text = item  // 設定 TextView 的文字
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.table_text_color))  // 設定文字顏色
                    setPadding(8, 8, 8, 8)  // 設定內邊距
                    layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)  // 設定寬度為0，並使用權重
                }
                tableRow.addView(textView)  // 將 TextView 添加到 TableRow
            }
            tableLayout.addView(tableRow)  // 將 TableRow 添加到 TableLayout
        }

        return root  // 返回根視圖
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // 清除綁定以避免內存洩漏
    }
}