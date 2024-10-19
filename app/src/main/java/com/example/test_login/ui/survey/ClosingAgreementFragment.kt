package com.example.test_login.ui.survey

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.myapplication.databinding.FragmentClosingAgreementBinding

class ClosingAgreementFragment : Fragment() {

    private var _binding: FragmentClosingAgreementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClosingAgreementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClosingAgreementBinding.inflate(inflater, container, false)

        // 初始化視圖
        setupViews()

        return binding.root
    }

    private fun setupViews() {
        // 使用 binding 來獲取視圖
        val inputName: TextInputEditText = binding.inputName
        val inputClosingDate: TextInputEditText = binding.inputClosingDate
        val inputIdNumber: TextInputEditText = binding.inputIdNumber
        val buttonSubmit: MaterialButton = binding.buttonSubmit
        val radioGroupClosingType: RadioGroup = binding.radioGroupClosingType
        val radioGroupMentalStatus: RadioGroup = binding.radioGroupMentalStatus
        val radioGroupClosingReason: RadioGroup = binding.radioGroupClosingReason
        val radioGroupLateralLink: RadioGroup = binding.radioGroupLateralLink

        // 設置按鈕點擊事件
        buttonSubmit.setOnClickListener {
            submitData(inputName, inputClosingDate, inputIdNumber, radioGroupClosingType, radioGroupMentalStatus, radioGroupClosingReason, radioGroupLateralLink)
        }

        // 設置下拉選單
        setupExposedDropdownMenu(binding.spinnerCommunityRehabilitationCenter, R.array.community_rehabilitation_center)
    }

    private fun submitData(
        inputName: TextInputEditText,
        inputClosingDate: TextInputEditText,
        inputIdNumber: TextInputEditText,
        radioGroupClosingType: RadioGroup,
        radioGroupMentalStatus: RadioGroup,
        radioGroupClosingReason: RadioGroup,
        radioGroupLateralLink: RadioGroup
    ) {
        // 獲取輸入數據
        val name = inputName.text.toString()
        val closingDate = inputClosingDate.text.toString()
        val idNumber = inputIdNumber.text.toString()

        // 獲取選擇的單選按鈕
        val closingType = radioGroupClosingType.checkedRadioButtonId
        val mentalStatus = radioGroupMentalStatus.checkedRadioButtonId
        val closingReason = radioGroupClosingReason.checkedRadioButtonId
        val lateralLink = radioGroupLateralLink.checkedRadioButtonId

        // 檢查必填項
        if (name.isEmpty() || closingDate.isEmpty() || idNumber.isEmpty() ||
            closingType == -1 || mentalStatus == -1 || closingReason == -1 || lateralLink == -1) {
            Toast.makeText(requireContext(), "請填寫所有必填項目", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(requireContext(), "數據已提交", Toast.LENGTH_SHORT).show()
    }

    private fun setupExposedDropdownMenu(autoCompleteTextView: AutoCompleteTextView, arrayResId: Int) {
        val options = resources.getStringArray(arrayResId)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, options)
        autoCompleteTextView.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 釋放綁定
    }
}