package com.example.test_login.ui.personalinfo

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentPersonalInfoBinding
import com.example.test_login.util.DateUtils

class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!

    // 使用 ViewModelFactory 创建 ViewModel 实例
    private val viewModel: PersonalInfoViewModel by viewModels {
        PersonalInfoViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 观察个人信息数据
        viewModel.personalInfo.observe(viewLifecycleOwner) { personalInfo ->
            bindDataToUI(personalInfo)
        }

        // 选择生日的点击事件
        binding.editTextBirthDate.setOnClickListener {
            showDatePickerDialog()
        }

        // 保存数据的点击事件
        binding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "資料已儲存", Toast.LENGTH_SHORT).show()
            savePersonalInfo()
            Log.d("PersonalInfoFragment", "Save button clicked")
        }
    }

    private fun bindDataToUI(personalInfo: PersonalInfo) {
        with(binding) {
            editTextName.setText(personalInfo.name)
            editTextIDNumber.setText(personalInfo.idNumber)
            editTextPhone.setText(personalInfo.phone)
            editTextEmail.setText(personalInfo.email)
            editTextUsername.setText(personalInfo.username)
            editTextOrganization.setText(personalInfo.organization)
            personalInfo.birthDate?.let { date ->
                editTextBirthDate.text = DateUtils.formatDate(date)
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                binding.editTextBirthDate.text = DateUtils.formatDate(year, month, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun savePersonalInfo() {
        Log.d("PersonalInfoFragment", "savePersonalInfo called")

        val birthDate = DateUtils.parseDate(binding.editTextBirthDate.text.toString())

        val personalInfo = PersonalInfo(
            name = binding.editTextName.text.toString(),
            idNumber = binding.editTextIDNumber.text.toString(),
            phone = binding.editTextPhone.text.toString(),
            email = binding.editTextEmail.text.toString(),
            username = binding.editTextUsername.text.toString(),
            organization = binding.editTextOrganization.text.toString(),
            birthDate = birthDate
        )

        viewModel.savePersonalInfo(personalInfo)
        Toast.makeText(requireContext(), "資料已儲存", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
