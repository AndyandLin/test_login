package com.example.test_login.ui.personalinfo

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentPersonalInfoBinding
import com.example.test_login.MainActivity
import com.example.test_login.addnewpower_page
import com.example.test_login.util.DateUtils

// 定義 PersonalInfoFragment 繼承自 Fragment
class PersonalInfoFragment : Fragment() {

    // 使用 Binding 管理 UI 元素，_binding 是一個可空的變數，binding 是非空的變數
    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!

    // 使用 ViewModelFactory 創建 ViewModel 實例
    private val viewModel: PersonalInfoViewModel by viewModels {
        PersonalInfoViewModelFactory(requireContext())
    }

    // 初始化 Fragment 的視圖，並設置 binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate 布局並返回根視圖
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 在視圖創建後，初始化一些行為和
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("PersonalInfoFragment", "Fragment is created and view is attached")
        binding.saveButton.isEnabled = true
        binding.saveButton.visibility = View.VISIBLE
        binding.saveButton.bringToFront()
        binding.saveButton.isClickable = true
        binding.saveButton.isFocusable = true

        // 當保存按鈕被點擊時，顯示提示信息，並保存個人資訊
        binding.saveButton.setOnClickListener{
            savePersonalInfo()
        }

        // 登出被點擊時執行以下代碼
        binding.logoutBtn.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        // 觀察 ViewModel 中的個人資訊數據變化，並將數據綁定到 UI 上
        viewModel.personalInfo.observe(viewLifecycleOwner) { personalInfo ->
            bindDataToUI(personalInfo)
        }

        // 當生日輸入框被點擊時，顯示日期選擇對話框
        binding.editTextBirthDate.setOnClickListener {
            showDatePickerDialog()
        }

    }

    // 將個人資訊綁定到 UI 上
    private fun bindDataToUI(personalInfo: PersonalInfo) {
        with(binding) {
            editTextName.setText(personalInfo.name)
            editTextIDNumber.setText(personalInfo.idNumber)
            editTextPhone.setText(personalInfo.phone)
            editTextEmail.setText(personalInfo.email)
            editTextUsername.setText(personalInfo.username)
            editTextOrganization.setText(personalInfo.organization)
            // 如果有生日日期，就格式化並顯示
            personalInfo.birthDate?.let { date ->
                editTextBirthDate.text = DateUtils.formatDate(date)
            }
        }
    }

    // 顯示日期選擇對話框
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // 當用戶選擇日期後，更新生日輸入框的文本
                binding.editTextBirthDate.text = DateUtils.formatDate(year, month, dayOfMonth)
            },
            // 預設顯示的年、月、日
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // 保存個人資訊到 ViewModel
    private fun savePersonalInfo() {
        Log.d("PersonalInfoFragment", "savePersonalInfo called")
        // 解析生日日期的文本為日期物件
        val birthDate = DateUtils.parseDate(binding.editTextBirthDate.text.toString())
        // 建立一個 PersonalInfo 物件，並填入各欄位的資料
        val personalInfo = PersonalInfo(
            name = binding.editTextName.text.toString(),
            idNumber = binding.editTextIDNumber.text.toString(),
            phone = binding.editTextPhone.text.toString(),
            email = binding.editTextEmail.text.toString(),
            username = binding.editTextUsername.text.toString(),
            organization = binding.editTextOrganization.text.toString(),
            birthDate = birthDate
        )

        // 將個人資訊傳遞給 ViewModel 儲存
        viewModel.savePersonalInfo(personalInfo)
        Toast.makeText(requireContext(), "資料已儲存", Toast.LENGTH_SHORT).show()
    }

    // 清理 Binding，防止記憶體洩漏
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
