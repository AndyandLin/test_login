package com.example.test_login.ui.healthsurvey

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHealthSurveyBinding

// 定義 HealthSurveyFragment 繼承自 Fragment
class HealthSurveyFragment : Fragment() {
    // 用於綁定 UI 元件的變數，初始化為 null，使用時需要確保不為 null
    private var _binding: FragmentHealthSurveyBinding? = null
    // 快速獲取 _binding 變數的非 null 值
    private val binding get() = _binding!!
    // 定義 ViewModel 變數，稍後會進行初始化
    private lateinit var viewModel: HealthSurveyViewModel

    // 當 Fragment 創建視圖時調用此方法
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用 DataBinding 工具來將 Fragment 與 XML 佈局進行綁定
        _binding = FragmentHealthSurveyBinding.inflate(inflater, container, false)
        // 根據綁定生成的 root 來設置 Fragment 的視圖
        val view = binding.root

        // 初始化 ViewModel，這裡使用 ViewModelProvider 並將 ViewModel 綁定至 activity 的生命週期
        viewModel = ViewModelProvider(requireActivity()).get(HealthSurveyViewModel::class.java)

        // 設置並初始化 UI 元件
        setupViews()

        // 返回此 Fragment 的根視圖
        return view
    }

    // 設置 UI 元件的初始化和事件處理
    private fun setupViews() {
        // 設置提交按鈕的點擊事件
        val submitButton: Button = binding.submitSurveyButton
        submitButton.setOnClickListener {
            // 保存調查問卷的回答並導航返回上一個界面
            saveSurveyAnswers()
            navigateBack()
        }

        // 設置 Spinner (下拉選單) 的選項
        val spinner1: Spinner = binding.answer1Spinner
        setupSpinner(spinner1, R.array.yes_no_options)
        // 其他 Spinner 也可以類似進行設置
    }

    // 初始化 Spinner 的方法，將選項綁定到指定的 Spinner
    private fun setupSpinner(spinner: Spinner, optionsArray: Int) {
        if (optionsArray != 0) {
            // 使用 ArrayAdapter 將選項數組資源綁定到 Spinner
            ArrayAdapter.createFromResource(
                requireContext(),
                optionsArray,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // 設置下拉選單的樣式
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // 將適配器綁定到 Spinner 上
                spinner.adapter = adapter
            }
        } else {
            // 如果傳入的選項資源無效，則記錄錯誤日誌
            Log.e("HealthSurveyFragment", "Invalid optionsArray ID: $optionsArray")
        }
    }

    // 保存用戶填寫的調查問卷答案
    private fun saveSurveyAnswers() {
        // 獲取 Spinner 的選擇項並轉換為字符串
        val answer1 = binding.answer1Spinner.selectedItem?.toString()
        // 檢查 ViewModel 是否已初始化
        if (::viewModel.isInitialized) {
            // 將回答添加到 ViewModel 的答案列表中
            viewModel.addSurveyAnswer(answer1)
            // 其他問題的回答也可以類似進行保存
        } else {
            // 如果 ViewModel 尚未初始化，則記錄錯誤日誌
            Log.e("HealthSurveyFragment", "ViewModel not initialized.")
        }
    }

    // 導航回到上一個 Fragment
    private fun navigateBack() {
        findNavController().popBackStack()
    }

    // 當 Fragment 的視圖銷毀時調用此方法
    override fun onDestroyView() {
        super.onDestroyView()
        // 銷毀綁定，以防止內存洩漏
        _binding = null
    }
}
