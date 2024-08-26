package com.example.test_login.ui.caseManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCaseManagementBinding

class CaseManagementFragment : Fragment() {

    private var _binding: FragmentCaseManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaseManagementBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Sample data (replace with actual data)
        val rowData = listOf(
            listOf("王小明", "編輯1", "A123456789", "2024-06-06", "健康問題", "進行中"),
            listOf("陳大同", "編輯2", "B987654321", "2024-06-05", "精神狀態", "進行中")
            // Add more rows as needed
        )

        // Find the TableLayout
        val tableLayout = root.findViewById<TableLayout>(R.id.TableLayout1)

        // Add rows to the TableLayout dynamically
        for (row in rowData) {
            val tableRow = TableRow(requireContext())

            for (item in row) {
                val textView = TextView(requireContext())
                textView.text = item
                textView.setTextColor(resources.getColor(R.color.table_text_color, null))
                textView.setPadding(8, 8, 8, 8)
                tableRow.addView(textView)
            }

            tableLayout.addView(tableRow)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
