package com.example.test_login.ui.surveyResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSurveyResultBinding


class SurveyResultFragment : Fragment() {

    private lateinit var viewModel: SurveyResultViewModel
    private lateinit var binding: FragmentSurveyResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurveyResultBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SurveyResultViewModel::class.java)

        displaySurveyResults()

        // 設定確認按鈕點擊事件，返回到 HealthSurveyFragment
        binding.confirmButton.setOnClickListener {
            Toast.makeText(requireContext(), "返回單張管理頁面", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_surveyResultFragment_to_editfileFragment)
        }

        return binding.root
    }

    private fun displaySurveyResults() {
        val results = viewModel.getSurveyAnswers() // 確保此方法已實作
        binding.nameTextView.text = results["姓名"] ?: "未知姓名"
        binding.birthdateTextView.text = results["出生年月日"] ?: "未知生日"
        binding.phoneTextView.text = results["聯絡電話"] ?: "未知電話"
        binding.addressTextView.text = results["通訊地址"] ?: "未知地址"
        binding.maritalStatusTextView.text = results["婚姻狀態"] ?: "未知婚姻狀態"
        binding.educationLevelTextView.text = results["教育程度"] ?: "未知教育程度"
        binding.economicStatusTextView.text = results["經濟狀況"] ?: "未知經濟狀況"
        binding.caseDateTextView.text = results["收案日期"] ?: "未知日期"
        binding.familyStatusTextView.text = results["與家人同住"] ?: "未知"
        binding.workTextView.text = results["工作"] ?: "未知"
        binding.religionTextView.text = results["宗教信仰"] ?: "未知"
        binding.locationTextView.text = results["收案地區"] ?: "未知地區"
    }
}
