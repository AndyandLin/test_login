package com.example.test_login.ui.task

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTaskBinding
import com.example.test_login.data.database.AppDatabase
import com.example.test_login.data.repository.TaskRepository
import com.example.test_login.network.model.RetrofitClient
import com.example.test_login.utils.DatabaseUtils

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        // 創建必要的依賴
        val database = AppDatabase.getInstance(requireContext())
        val taskDao = database.taskDao()
        val apiService = RetrofitClient.apiService
        val repository = TaskRepository(taskDao, apiService)

        // 使用 repository 創建 ViewModel
        viewModel = ViewModelProvider(this, TaskViewModelFactory(repository))
            .get(TaskViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatabaseTools()
        observeTasks()
    }

    private fun setupDatabaseTools() {
        // 添加數據庫工具按鈕
        binding.apply {
            // 檢查數據庫按鈕
            checkDatabaseButton.setOnClickListener {
                DatabaseUtils.checkDatabase(requireContext())
                Toast.makeText(context, "正在檢查數據庫，請查看 Logcat", Toast.LENGTH_SHORT).show()
            }

            // 清空數據庫按鈕
            clearDatabaseButton.setOnClickListener {
                showClearDatabaseConfirmDialog()
            }

            // 添加測試數據按鈕
            addTestDataButton.setOnClickListener {
                addTestTask()
            }
        }
    }

    private fun observeTasks() {
        // 觀察任務列表變化
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            // 更新 UI 顯示任務列表
            tasks.forEach { task ->
                // 這裡可以添加更新 RecyclerView 或其他 UI 元素的代碼
            }
        }
    }

    private fun showClearDatabaseConfirmDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("清空數據庫")
            .setMessage("確定要清空所有數據嗎？此操作無法撤銷。")
            .setPositiveButton("確定") { _, _ ->
                DatabaseUtils.clearDatabase(requireContext())
                Toast.makeText(context, "數據庫已清空", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun addTestTask() {
        viewModel.addTask(
            title = "測試任務 ${System.currentTimeMillis()}",
            description = "這是一個測試任務"
        )
        Toast.makeText(context, "已添加測試任務", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}