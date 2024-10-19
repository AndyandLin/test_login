package com.example.test_login.ui.healthsurvey

import android.app.AlertDialog
import android.os.Bundle
import android.app.DatePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHealthSurveyBinding
import com.example.test_login.ui.surveyResult.SurveyResultViewModel
import java.util.Calendar

// 定義 HealthSurveyFragment，繼承自 Fragment
class HealthSurveyFragment : Fragment() {
    // 透過 DataBinding 綁定 UI 元件，初始值為 null
    private var _binding: FragmentHealthSurveyBinding? = null
    // 透過 Kotlin 特性確保 _binding 非 null 時能直接使用
    private val binding get() = _binding!!

    // 定義 ViewModel，用來儲存和處理調查問卷的資料
    private lateinit var healthViewModel: HealthSurveyViewModel
    private lateinit var resultViewModel: SurveyResultViewModel // 新增 SurveyResultViewModel

    // 當 Fragment 被創建時，會首先執行這個方法來加載並建立 UI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用 DataBinding 綁定 XML 佈局
        _binding = FragmentHealthSurveyBinding.inflate(inflater, container, false)
        // 將 Fragment 的根視圖設為綁定的 root
        val view = binding.root

        // 初始化 ViewModel，並將其綁定到 activity 的生命週期
        healthViewModel = ViewModelProvider(requireActivity()).get(HealthSurveyViewModel::class.java)
        resultViewModel = ViewModelProvider(requireActivity()).get(SurveyResultViewModel::class.java) // 初始化 SurveyResultViewModel

        // 呼叫方法初始化 UI 元件
        setupViews()

        // 設置日期選擇器
        setupDatePickers()

        // 返回這個 Fragment 的根視圖
        return view
    }

    // 設定 UI 元件以及其行為
    private fun setupViews() {
        // 設定提交按鈕的點擊事件
        val submitButton: Button = binding.submitButton
        submitButton.setOnClickListener {
            // 點擊提交按鈕時保存問卷答案並導航到 SurveyResultFragment
            saveSurveyAnswers()
        }

        setupExposedDropdownMenu(binding.maleAutoCompleteTextView, R.array.gender_options)
        // 婚姻狀態
        setupExposedDropdownMenu(binding.maritalStatusAutoCompleteTextView, R.array.marital_status_options)
        // 教育程度
        setupExposedDropdownMenu(binding.educationLevelAutoCompleteTextView, R.array.education_level_options)
        // 經濟狀況
        setupExposedDropdownMenu(binding.economicStatusAutoCompleteTextView, R.array.economic_status_options)
    }

    private fun setupExposedDropdownMenu(autoCompleteTextView: AutoCompleteTextView, optionsArray: Int) {
        val items = resources.getStringArray(optionsArray)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
    }

    // 設置日期選擇器的方法
    private fun setupDatePickers() {
        // 設置出生年月日的日期選擇
        binding.birthdateEditText.setOnClickListener {
            showDatePickerDialog(binding.birthdateEditText)
        }

        // 設置收案日期的日期選擇
        binding.caseDateEditText.setOnClickListener {
            showDatePickerDialog(binding.caseDateEditText)
        }
    }

    // 顯示日期選擇器
    private fun showDatePickerDialog(editText: EditText) {
        // 獲取當前日期
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // 創建並顯示 DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // 將選取的日期設置到 EditText 中
                val formattedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay"
                editText.setText(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    // 保存使用者填寫的問卷回答
    private fun saveSurveyAnswers() {
        // 獲取所有的使用者輸入資料
        val name = binding.nameEditText.text.toString().trim() // 姓名
        val gender = binding.maleAutoCompleteTextView.text.toString() // 性別
        val birthdate = binding.birthdateEditText.text.toString().trim() // 出生年月日
        val phone = binding.phoneEditText.text.toString().trim() // 聯絡電話
        val address = binding.addressEditText.text.toString().trim() // 通訊地址
        val caseDate = binding.caseDateEditText.text.toString().trim()  // 收案日期

        // 婚姻狀態、教育程度、經濟狀況 的選擇結果
        val maritalStatus = binding.maritalStatusAutoCompleteTextView.text.toString()
        val educationLevel = binding.educationLevelAutoCompleteTextView.text.toString()
        val economicStatus = binding.economicStatusAutoCompleteTextView.text.toString()

        // 與家人同住的選擇
        val livingWithFamily: String = when (binding.livingWithFamilyRadioGroup.checkedRadioButtonId) {
            R.id.livingWithFamilyNoRadioButton -> "無"
            R.id.livingWithFamilyYesRadioButton -> {
                val relation = binding.familyRelationEditText.text.toString().trim()
                if (relation.isNotEmpty()) "有，關係：$relation" else "有"
            }
            else -> "未選擇"
        }

        // 工作狀態的選擇
        val jobStatus: String = when (binding.jobRadioGroup.checkedRadioButtonId) {
            R.id.jobNoneRadioButton -> "無"
            R.id.jobFullTimeRadioButton -> "全職"
            R.id.jobPartTimeRadioButton -> "兼職"
            else -> "未選擇"
        }

        // 宗教信仰的選擇
        val religion: String = when (binding.religionRadioGroup.checkedRadioButtonId) {
            R.id.religionNoneRadioButton -> "無"
            R.id.religionBuddhismRadioButton -> "佛教"
            R.id.religionTaoismRadioButton -> "道教"
            R.id.religionChristianityRadioButton -> "基督教"
            R.id.religionCatholicRadioButton -> "天主教"
            R.id.religionIslamRadioButton -> "回教"
            R.id.religionOtherRadioButton -> {
                val otherReligion = binding.otherReligionEditText.text.toString().trim()
                if (otherReligion.isNotEmpty()) otherReligion else "其他"
            }
            else -> "未選擇"
        }

        // 收案地區的選擇
        val region: String = when (binding.regionRadioGroup.checkedRadioButtonId) {
            R.id.regionNantouRadioButton -> "南投"
            R.id.regionCaotunRadioButton -> "草屯"
            R.id.regionGuoxingRadioButton -> "國姓"
            R.id.regionPuliRadioButton -> "埔里"
            R.id.regionZhushanRadioButton -> "竹山"
            R.id.regionShuiliRadioButton -> "水里"
            R.id.regionDaliRadioButton -> "大里"
            R.id.regionWufengRadioButton -> "霧峰"
            R.id.regionMainHospitalRadioButton -> "本院"
            R.id.regionOtherRadioButton -> {
                val otherRegion = binding.otherRegionEditText.text.toString().trim()
                if (otherRegion.isNotEmpty()) otherRegion else "其他"
            }
            else -> "未選擇"
        }

        // 檢查必填欄位是否有填寫
        if (name.isNotEmpty() && gender.isNotEmpty() && birthdate.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()  && jobStatus.isNotEmpty()
            && maritalStatus.isNotEmpty() && educationLevel.isNotEmpty() && economicStatus.isNotEmpty() && caseDate.isNotEmpty() && livingWithFamily.isNotEmpty()
            && religion.isNotEmpty() && region.isNotEmpty() ) {
            // 將使用者的回答存入 ViewModel
            healthViewModel.setName(name)
            healthViewModel.setGender(gender)
            healthViewModel.setBirthdate(birthdate)
            healthViewModel.setPhone(phone)
            healthViewModel.setAddress(address)
            healthViewModel.setMaritalStatus(maritalStatus)
            healthViewModel.setEducationLevel(educationLevel)
            healthViewModel.setEconomicStatus(economicStatus)
            healthViewModel.setCaseDate(caseDate)
            healthViewModel.setLivingWithFamily(livingWithFamily)
            healthViewModel.setJobStatus(jobStatus)
            healthViewModel.setReligion(religion)
            healthViewModel.setRegion(region)

            // 存儲問卷答案到結果 ViewModel
            resultViewModel.setSurveyAnswers(mapOf(
                "姓名" to name,
                "性別" to gender,
                "出生年月日" to birthdate,
                "聯絡電話" to phone,
                "通訊地址" to address,
                "婚姻狀態" to maritalStatus,
                "教育程度" to educationLevel,
                "經濟狀況" to economicStatus,
                "收案日期" to caseDate,
                "與家人同住" to livingWithFamily,
                "工作" to jobStatus,
                "宗教信仰" to religion,
                "收案地區" to region
            ))

            // 檢查問卷是否填寫完整
            if (healthViewModel.isSurveyComplete()) {
                navigateToSurveyResult() // 導航到結果頁面
            } else {
                showAlertDialog("請填寫所有必填欄位") // 顯示提示訊息
                Log.d("HealthSurveyFragment", "問卷未填寫完整，顯示提示訊息") // 添加 Log 訊息
            }
        } else {
            showAlertDialog("請填寫所有必填欄位") // 顯示提示訊息
            Log.d("HealthSurveyFragment", "必填欄位未填寫，顯示提示訊息") // 添加 Log 訊息
        }
    }

    // 導航到 SurveyResultFragment
    private fun navigateToSurveyResult() {
        findNavController().navigate(R.id.action_healthSurveyFragment_to_surveyResultFragment)
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("確定") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // 當 Fragment 的視圖被銷毀時調用這個方法
    override fun onDestroyView() {
        super.onDestroyView()
        // 銷毀綁定以防止內存洩漏
        _binding = null
    }
}