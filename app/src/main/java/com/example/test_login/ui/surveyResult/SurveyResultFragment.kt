package com.example.test_login.ui.surveyResult

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSurveyResultBinding

class SurveyResultFragment : Fragment() {

    // 使用 activityViewModels 確保與其他 Fragment 共享相同的 ViewModel 實例
    private val viewModel: SurveyResultViewModel by activityViewModels()
    private var _binding: FragmentSurveyResultBinding? = null
    private val binding get() = _binding!! // 確保僅在有效生命周期內訪問

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SurveyResultFragment", "onCreateView called")
        _binding = FragmentSurveyResultBinding.inflate(inflater, container, false)

        // 獲取 ViewModel 中的數據並顯示
        displaySurveyResults()

        // 設置按鈕事件
        setupConfirmButton()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 釋放 binding 以防止內存洩漏
    }

    // 設定確認按鈕的點擊事件
    private fun setupConfirmButton() {
        binding.confirmButton.setOnClickListener {
            Toast.makeText(requireContext(), "返回單張管理頁面", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_surveyResultFragment_to_editfileFragment)
        }
    }

    // 顯示調查結果的方法
    private fun displaySurveyResults() {
        val results = viewModel.getSurveyAnswers() ?: emptyMap() // 提供默認值避免 NullPointerException
        Log.d("SurveyResultFragment", "Survey Results: $results")

        if (results.isEmpty()) {
            Log.e("SurveyResultFragment", "Survey results are empty!") // 結果為空時的日誌
            displayDefaultValues() // 顯示預設值
        } else {
            populateSurveyResults(results) // 將數據填充到 UI
        }
    }

    // 填充調查結果到 UI
    private fun populateSurveyResults(results: Map<String, String>) {
        // 使用安全調用運算子來避免 NullPointerException
        binding.nameTextView.text = results["姓名"] ?: getString(R.string.unknown_name)
        binding.birthdateTextView.text = results["出生年月日"] ?: getString(R.string.unknown_birthday)
        binding.phoneTextView.text = results["聯絡電話"] ?: getString(R.string.unknown_phone)
        binding.addressTextView.text = results["通訊地址"] ?: getString(R.string.unknown_address)
        binding.maritalStatusTextView.text = results["婚姻狀態"] ?: getString(R.string.unknown_marital_status)
        binding.educationLevelTextView.text = results["教育程度"] ?: getString(R.string.unknown_education)
        binding.economicStatusTextView.text = results["經濟狀況"] ?: getString(R.string.unknown_economic_status)
        binding.caseDateTextView.text = results["收案日期"] ?: getString(R.string.unknown_case_date)
        binding.familyStatusTextView.text = results["與家人同住"] ?: getString(R.string.unknown_family_status)
        binding.workTextView.text = results["工作"] ?: getString(R.string.unknown_work)
        binding.religionTextView.text = results["宗教信仰"] ?: getString(R.string.unknown_religion)
        binding.locationTextView.text = results["收案地區"] ?: getString(R.string.unknown_location)
    }

    // 顯示預設值的方法
    private fun displayDefaultValues() {
        binding.nameTextView.text = getString(R.string.unknown_name)
        binding.birthdateTextView.text = getString(R.string.unknown_birthday)
        binding.phoneTextView.text = getString(R.string.unknown_phone)
        binding.addressTextView.text = getString(R.string.unknown_address)
        binding.maritalStatusTextView.text = getString(R.string.unknown_marital_status)
        binding.educationLevelTextView.text = getString(R.string.unknown_education)
        binding.economicStatusTextView.text = getString(R.string.unknown_economic_status)
        binding.caseDateTextView.text = getString(R.string.unknown_case_date)
        binding.familyStatusTextView.text = getString(R.string.unknown_family_status)
        binding.workTextView.text = getString(R.string.unknown_work)
        binding.religionTextView.text = getString(R.string.unknown_religion)
        binding.locationTextView.text = getString(R.string.unknown_location)
    }
}
