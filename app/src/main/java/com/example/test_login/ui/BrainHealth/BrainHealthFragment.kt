package com.example.test_login.ui.BrainHealth

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBrainHealthBinding
import com.example.test_login.network.api.ApiService
import com.example.test_login.ui.surveyResult.SurveyResultViewModel
import com.example.test_login.util.DateUtils.formatDate
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import androidx.lifecycle.lifecycleScope
import com.example.test_login.data.database.AppDatabase
import com.example.test_login.data.model.BrainHealthEntity
import com.example.test_login.network.model.response.BrainHealthRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

/**
 * 大腦健康問卷 Fragment
 * 負責收集用戶填寫的問卷資料並提交到伺服器
 */
class BrainHealthFragment : Fragment() {

    // ViewBinding 相關
    private var _binding: FragmentBrainHealthBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    // ViewModel 相關
    private val healthViewModel: BrainHealthViewModel by viewModels()
    private val resultViewModel: SurveyResultViewModel by activityViewModels()

    // API 服務
    private lateinit var apiService: ApiService

    // 網路變化監聽器
    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (isNetworkAvailable()) {
                syncSurveys() // 如果網路可用，進行問卷同步
            }
        }
    }

    // 是否是首次加載
    private var isFirstLoad = true

    // Fragment 生命週期方法
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrainHealthBinding.inflate(inflater, container, false)

        // 清空之前的問卷數據
        healthViewModel.clearSurvey()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupApiService() // 設置 API 服務
        setupViews() // 設置視圖
        setupObservers() // 設置觀察者
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter) // 註冊網路變化監聽器
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(networkChangeReceiver) // 注銷網路變化監聽器
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 初始化和設置方法
    private fun setupViews() {
        setupDropdownMenus() // 設置下拉菜單
        setupDatePickers() // 設置日期選擇器
        setupSubmitButton() // 設置提交按鈕
    }

    private fun setupApiService() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(ApiService::class.java) // 創建 API 服務實例
    }

    private fun setupObservers() {
        healthViewModel.surveyData.observe(viewLifecycleOwner) { data ->
            if (isFirstLoad) {
                isFirstLoad = false
                return@observe // 首次加載時跳過 UI 綁定
            }

            data?.let {
                updateUI(it) // 僅在非首次加載時更新 UI
            }
        }
    }

    private fun updateUI(data: BrainHealthData) {
        // 更新 UI 元素
        binding.nameEditText.setText(data.name)
        binding.maleAutoCompleteTextView.setText(data.gender)
        binding.birthdateEditText.setText(data.birthdate)
        binding.phoneEditText.setText(data.phone)
        binding.addressEditText.setText(data.address)
        binding.maritalStatusAutoCompleteTextView.setText(data.maritalStatus)
        binding.educationLevelAutoCompleteTextView.setText(data.educationLevel)
        binding.economicStatusAutoCompleteTextView.setText(data.economicStatus)
        binding.caseDateEditText.setText(data.caseDate)

        // 更新與家人同住的選擇
        updateLivingWithFamily(data.livingWithFamily)

        // 更新工作狀態
        updateJobStatus(data.jobStatus)

        // 更新宗教信仰
        updateReligion(data.religion)

        // 更新地區
        updateRegion(data.region)

        // 更新問卷問題的選擇
        updateQuestions(data)
    }

    private fun updateLivingWithFamily(livingWithFamily: String?) {
        when (livingWithFamily) {
            "無" -> binding.livingWithFamilyRadioGroup.check(R.id.livingWithFamilyNoRadioButton)
            else -> {
                binding.livingWithFamilyRadioGroup.check(R.id.livingWithFamilyYesRadioButton)
                val relation = livingWithFamily?.substringAfter("關係：") ?: "-"
                binding.familyRelationEditText.setText(relation)
            }
        }
    }

    private fun updateJobStatus(jobStatus: String?) {
        when (jobStatus) {
            "無" -> binding.jobRadioGroup.check(R.id.jobNoneRadioButton)
            "全職" -> binding.jobRadioGroup.check(R.id.jobFullTimeRadioButton)
            "兼職" -> binding.jobRadioGroup.check(R.id.jobPartTimeRadioButton)
        }
    }

    private fun updateReligion(religion: String?) {
        when (religion) {
            "無" -> binding.religionRadioGroup.check(R.id.religionNoneRadioButton)
            "佛教" -> binding.religionRadioGroup.check(R.id.religionBuddhismRadioButton)
            "道教" -> binding.religionRadioGroup.check(R.id.religionTaoismRadioButton)
            "基督教" -> binding.religionRadioGroup.check(R.id.religionChristianityRadioButton)
            "天主教" -> binding.religionRadioGroup.check(R.id.religionCatholicRadioButton)
            "回教" -> binding.religionRadioGroup.check(R.id.religionIslamRadioButton)
            else -> {
                binding.religionRadioGroup.check(R.id.religionOtherRadioButton)
                binding.otherReligionEditText.setText(religion)
            }
        }
    }

    private fun updateRegion(region: String?) {
        when (region) {
            "南投" -> binding.regionRadioGroup.check(R.id.regionNantouRadioButton)
            "草屯" -> binding.regionRadioGroup.check(R.id.regionCaotunRadioButton)
            "國姓" -> binding.regionRadioGroup.check(R.id.regionGuoxingRadioButton)
            "埔里" -> binding.regionRadioGroup.check(R.id.regionPuliRadioButton)
            "竹山" -> binding.regionRadioGroup.check(R.id.regionZhushanRadioButton)
            "水里" -> binding.regionRadioGroup.check(R.id.regionShuiliRadioButton)
            "大里" -> binding.regionRadioGroup.check(R.id.regionDaliRadioButton)
            "霧峰" -> binding.regionRadioGroup.check(R.id.regionWufengRadioButton)
            "本院" -> binding.regionRadioGroup.check(R.id.regionMainHospitalRadioButton)
            else -> {
                binding.regionRadioGroup.check(R.id.regionOtherRadioButton)
                binding.otherRegionEditText.setText(region)
            }
        }
    }

    private fun updateQuestions(data: BrainHealthData) {
        binding.question1RadioGroup.check(if (data.q1) R.id.question1Yes else R.id.question1No)
        binding.question2RadioGroup.check(if (data.q2) R.id.question2Yes else R.id.question2No)
        binding.question3RadioGroup.check(if (data.q3) R.id.question3Yes else R.id.question3No)
        binding.question4RadioGroup.check(if (data.q4) R.id.question4Yes else R.id.question4No)
        binding.question5RadioGroup.check(if (data.q5) R.id.question5Yes else R.id.question5No)
        binding.question6RadioGroup.check(if (data.q6) R.id.question6Yes else R.id.question6No)
        binding.question7RadioGroup.check(if (data.q7) R.id.question7Yes else R.id.question7No)
        binding.question8RadioGroup.check(if (data.q8) R.id.question8Yes else R.id.question8No)
        binding.question9RadioGroup.check(if (data.q9) R.id.question9Yes else R.id.question9No)
        binding.question10RadioGroup.check(if (data.q10) R.id.question10Yes else R.id.question10No)
        binding.question11RadioGroup.check(if (data.q11) R.id.question11Yes else R.id.question11No)
        binding.question12RadioGroup.check(if (data.q12) R.id.question12Yes else R.id.question12No)
        binding.question13RadioGroup.check(if (data.q13) R.id.question13Yes else R.id.question13No)
        binding.question14RadioGroup.check(if (data.q14) R.id.question14Yes else R.id.question14No)
        binding.question15RadioGroup.check(if (data.q15) R.id.question15Yes else R.id.question15No)
    }

    // 使用 with 作用域函數來簡化代碼
    private fun setupDropdownMenus() {
        with(binding) {
            setupDropdownMenu(maleAutoCompleteTextView, R.array.gender_options)
            setupDropdownMenu(maritalStatusAutoCompleteTextView, R.array.marital_status_options)
            setupDropdownMenu(educationLevelAutoCompleteTextView, R.array.education_level_options)
            setupDropdownMenu(economicStatusAutoCompleteTextView, R.array.economic_status_options)
        }
    }

    // 抽取下拉選單設置邏輯，減少重複代碼
    private fun setupDropdownMenu(view: AutoCompleteTextView, arrayResId: Int) {
        val items = resources.getStringArray(arrayResId)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        view.setAdapter(adapter)
    }

    // 提交按鈕相關邏輯
    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    if (validateInputs()) {
                        val surveyMap = prepareSurveyMap() // 準備問卷數據
                        resultViewModel.setSurveyAnswers(surveyMap) // 更新 ViewModel
                        submitSurvey() // 提交問卷
                        navigateToResult() // 導航到結果頁面
                    } else {
                        Toast.makeText(requireContext(), "請完成所有必填字段", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("BrainHealthFragment", "提交問卷時出錯", e)
                    Toast.makeText(requireContext(), "提交問卷失敗，請稍後再試", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun prepareSurveyMap(): Map<String, String> {
        val surveyData = collectSurveyData() // 獲取問卷數據
        return mapOf(
            "姓名" to (surveyData.name ?: "未知姓名"),
            "性別" to (surveyData.gender ?: "未知性別"),
            "出生年月日" to (surveyData.birthdate ?: "未知生日"),
            "聯絡電話" to (surveyData.phone ?: "未知電話"),
            "通訊地址" to (surveyData.address ?: "未知地址"),
            "婚姻狀態" to (surveyData.maritalStatus ?: "未知婚姻狀態"),
            "教育程度" to (surveyData.educationLevel ?: "未知教育程度"),
            "經濟狀況" to (surveyData.economicStatus ?: "未知經濟狀況"),
            "收案日期" to (surveyData.caseDate ?: "未知日期"),
            "與家人同住" to (surveyData.livingWithFamily ?: "未知"),
            "工作" to (surveyData.jobStatus ?: "未知"),
            "宗教信仰" to (surveyData.religion ?: "未知"),
            "收案地區" to (surveyData.region ?: "未知地區")
        )
    }

    // 設置日期選擇器的方法
    private fun setupDatePickers() {
        binding.birthdateEditText.setOnClickListener {
            showDatePickerDialog(binding.birthdateEditText)
        }
        binding.caseDateEditText.setOnClickListener {
            showDatePickerDialog(binding.caseDateEditText)
        }
    }

    // UI 相關方法
    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val formattedDate = formatDate(year, month + 1, day)
                editText.setText(formattedDate) // 設置選擇的日期
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // 顯示載入中狀態
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.submitButton.isEnabled = false
    }

    // 隱藏載入中狀態
    private fun hideLoading() {
        binding?.let {
            it.progressBar.visibility = View.GONE
            it.submitButton.isEnabled = true
        }
    }

    // 統一的錯誤提示對話框
    private fun showError(message: String) {
        showAlertDialog(message)
    }

    // 統一的 Toast 提示
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("確定", null)
            .show()
    }

    // 保存使用者填寫的問卷回答
    private fun collectSurveyData(): BrainHealthData {
        // 收集用戶輸入的資料
        val name = binding.nameEditText.text.toString().trim()
        val gender = binding.maleAutoCompleteTextView.text.toString().trim()
        val birthdate = binding.birthdateEditText.text.toString().trim()
        val phone = binding.phoneEditText.text.toString().trim()
        val address = binding.addressEditText.text.toString().trim()
        val maritalStatus = binding.maritalStatusAutoCompleteTextView.text.toString().trim()
        val educationLevel = binding.educationLevelAutoCompleteTextView.text.toString().trim()
        val economicStatus = binding.economicStatusAutoCompleteTextView.text.toString().trim()
        val caseDate = binding.caseDateEditText.text.toString().trim()

        // 檢查必填欄位
        if (name.isEmpty() || gender.isEmpty() || phone.isEmpty() || educationLevel.isEmpty() || maritalStatus.isEmpty() || economicStatus.isEmpty()) {
            throw IllegalArgumentException("請填寫所有必填欄位")
        }

        // 返回收集到的問卷資料
        return BrainHealthData(
            name = name,
            gender = gender,
            birthdate = birthdate,
            phone = phone,
            address = address,
            maritalStatus = maritalStatus,
            educationLevel = educationLevel,
            economicStatus = economicStatus,
            caseDate = caseDate,
            livingWithFamily = when (binding.livingWithFamilyRadioGroup.checkedRadioButtonId) {
                R.id.livingWithFamilyNoRadioButton -> "無"
                R.id.livingWithFamilyYesRadioButton -> {
                    val relation = binding.familyRelationEditText.text.toString().trim()
                    if (relation.isNotEmpty()) "有，關係：$relation" else "有"
                }
                else -> "未知" // 提供默認值
            },
            jobStatus = when (binding.jobRadioGroup.checkedRadioButtonId) {
                R.id.jobNoneRadioButton -> "無"
                R.id.jobFullTimeRadioButton -> "全職"
                R.id.jobPartTimeRadioButton -> "兼職"
                else -> "未知" // 提供默認值
            },
            religion = when (binding.religionRadioGroup.checkedRadioButtonId) {
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
                else -> "未知" // 提供默認值
            },
            region = when (binding.regionRadioGroup.checkedRadioButtonId) {
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
                else -> "未知" // 提供默認值
            },
            q1 = binding.question1RadioGroup.checkedRadioButtonId == R.id.question1Yes,
            q2 = binding.question2RadioGroup.checkedRadioButtonId == R.id.question2Yes,
            q3 = binding.question3RadioGroup.checkedRadioButtonId == R.id.question3Yes,
            q4 = binding.question4RadioGroup.checkedRadioButtonId == R.id.question4Yes,
            q5 = binding.question5RadioGroup.checkedRadioButtonId == R.id.question5Yes,
            q6 = binding.question6RadioGroup.checkedRadioButtonId == R.id.question6Yes,
            q7 = binding.question7RadioGroup.checkedRadioButtonId == R.id.question7Yes,
            q8 = binding.question8RadioGroup.checkedRadioButtonId == R.id.question8Yes,
            q9 = binding.question9RadioGroup.checkedRadioButtonId == R.id.question9Yes,
            q10 = binding.question10RadioGroup.checkedRadioButtonId == R.id.question10Yes,
            q11 = binding.question11RadioGroup.checkedRadioButtonId == R.id.question11Yes,
            q12 = binding.question12RadioGroup.checkedRadioButtonId == R.id.question12Yes,
            q13 = binding.question13RadioGroup.checkedRadioButtonId == R.id.question13Yes,
            q14 = binding.question14RadioGroup.checkedRadioButtonId == R.id.question14Yes,
            q15 = binding.question15RadioGroup.checkedRadioButtonId == R.id.question15Yes
        )
    }

    private suspend fun submitSurvey() {
        val surveyData = collectSurveyData() // 獲取問卷數據

        if (!isNetworkAvailable()) {
            saveSurveyLocally(surveyData) // 本地保存數據
            return
        }

        // 網絡可用，提交數據到服務器
        showLoading()
        try {
            val request = prepareSurveyRequest(surveyData) // 構建提交請求
            submitSurveyData(request) // 提交問卷數據到服務器
            hideLoading()
            showToast("問卷提交成功") // 用戶提示
        } catch (e: Exception) {
            hideLoading()
            Log.e("BrainHealthFragment", "問卷提交失敗", e)
            showToast("問卷提交失敗，請稍後重試")
        }
    }

    private suspend fun saveSurveyLocally(surveyData: BrainHealthData) {
        try {
            val questionsJson = serializeQuestions(surveyData) // 序列化問題答案
            val entity = BrainHealthEntity(
                name = requireNotNull(surveyData.name) { "姓名不能為空" },
                gender = requireNotNull(surveyData.gender) { "性別不能為空" },
                birthdate = surveyData.birthdate ?: "未知",
                phone = surveyData.phone ?: "未知",
                address = surveyData.address ?: "未知",
                maritalStatus = surveyData.maritalStatus ?: "未知",
                educationLevel = surveyData.educationLevel ?: "未知",
                economicStatus = surveyData.economicStatus ?: "未知",
                caseDate = surveyData.caseDate ?: "未知",
                livingWithFamily = surveyData.livingWithFamily ?: "未知",
                jobStatus = surveyData.jobStatus ?: "未知",
                religion = surveyData.religion ?: "未知",
                region = surveyData.region ?: "未知",
                questions = questionsJson // 存儲問題答案的 JSON
            )

            val db = AppDatabase.getInstance(requireContext())
            db.brainHealthDao().insert(entity) // 保存到本地數據庫

            showToast("已儲存問卷資料，待網路恢復後自動同步") // 提供用戶反饋
        } catch (e: Exception) {
            Log.e("BrainHealthFragment", "本地保存問卷失敗", e)
            showToast("本地保存問卷失敗，請稍後重試")
        }
    }

    private fun prepareSurveyRequest(surveyData: BrainHealthData): BrainHealthRequest {
        return BrainHealthRequest(
            name = surveyData.name ?: "未知姓名",
            gender = surveyData.gender ?: "未知性別",
            birth = surveyData.birthdate ?: "未知",
            correspondenceAddress = surveyData.address ?: "未知",
            contactNumber = surveyData.phone ?: "未知",
            education = surveyData.educationLevel ?: "未知",
            maritalStatus = surveyData.maritalStatus ?: "未知",
            economicSituation = surveyData.economicStatus ?: "未知",
            caseDate = surveyData.caseDate ?: "未知",
            liveTogether = surveyData.livingWithFamily?.startsWith("有") ?: false,
            work = when (surveyData.jobStatus) {
                "無" -> 0
                "全職" -> 1
                "兼職" -> 2
                else -> 0
            },
            religion = when (surveyData.religion) {
                "無" -> 0
                "佛教" -> 1
                "道教" -> 2
                "基督教" -> 3
                "天主教" -> 4
                "回教" -> 5
                else -> 6
            },
            area = when (surveyData.region) {
                "南投" -> 1
                "草屯" -> 2
                "國姓" -> 3
                "埔里" -> 4
                "竹山" -> 5
                "水里" -> 6
                "大里" -> 7
                "霧峰" -> 8
                "本院" -> 9
                else -> 10
            },
            questions = listOf(
                surveyData.q1, surveyData.q2, surveyData.q3, surveyData.q4, surveyData.q5,
                surveyData.q6, surveyData.q7, surveyData.q8, surveyData.q9, surveyData.q10,
                surveyData.q11, surveyData.q12, surveyData.q13, surveyData.q14, surveyData.q15
            )
        )
    }

    private fun serializeQuestions(surveyData: BrainHealthData): String {
        return Gson().toJson(
            listOf(
                surveyData.q1, surveyData.q2, surveyData.q3, surveyData.q4, surveyData.q5,
                surveyData.q6, surveyData.q7, surveyData.q8, surveyData.q9, surveyData.q10,
                surveyData.q11, surveyData.q12, surveyData.q13, surveyData.q14, surveyData.q15
            )
        )
    }

    private fun syncSurveys() {
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getInstance(requireContext())
                val surveys = db.brainHealthDao().getAll() // 獲取所有問卷

                for (survey in surveys) {
                    // 將問卷問題從 JSON 解析為列表
                    val questionsList: List<Boolean> = Gson().fromJson(
                        survey.questions,
                        object : TypeToken<List<Boolean>>() {}.type
                    )

                    // 構建請求對象
                    val request = BrainHealthRequest(
                        name = survey.name,
                        gender = survey.gender,
                        birth = survey.birthdate,
                        correspondenceAddress = survey.address,
                        contactNumber = survey.phone,
                        education = survey.educationLevel,
                        maritalStatus = survey.maritalStatus,
                        economicSituation = survey.economicStatus,
                        caseDate = survey.caseDate,
                        liveTogether = survey.livingWithFamily?.startsWith("有") ?: false,
                        work = mapJobStatus(survey.jobStatus),
                        religion = mapReligion(survey.religion),
                        area = mapRegion(survey.region),
                        questions = questionsList
                    )

                    // 提交問卷數據
                    submitSurveyData(request)

                    // 同步成功後刪除已同步的資料
                    db.brainHealthDao().delete(survey) // 假設你有一個 delete 方法
                }
            } catch (e: Exception) {
                Log.e("BrainHealthFragment", "同步問卷失敗", e)
                Toast.makeText(requireContext(), "同步問卷時發生錯誤", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 將工作狀態映射為數字
    private fun mapJobStatus(jobStatus: String?): Int {
        return when (jobStatus) {
            "無" -> 0
            "全職" -> 1
            "兼職" -> 2
            else -> 0
        }
    }

    // 將宗教信仰映射為數字
    private fun mapReligion(religion: String?): Int {
        return when (religion) {
            "無" -> 0
            "佛教" -> 1
            "道教" -> 2
            "基督教" -> 3
            "天主教" -> 4
            "回教" -> 5
            else -> 6
        }
    }

    // 將地區映射為數字
    private fun mapRegion(region: String?): Int {
        return when (region) {
            "南投" -> 1
            "草屯" -> 2
            "國姓" -> 3
            "埔里" -> 4
            "竹山" -> 5
            "水里" -> 6
            "大里" -> 7
            "霧峰" -> 8
            "本院" -> 9
            else -> 10
        }
    }

    private suspend fun submitSurveyData(request: BrainHealthRequest) {
        try {
            val response = apiService.submitBrainHealth(request)
            if (response.isSuccessful) {
                // 確保在更新 UI 前檢查 binding
                if (isAdded && view != null) {
                    handleSuccessfulResponse(request)
                }
            } else {
                handleApiError(Exception("Error code: ${response.code()}"))
            }
        } catch (t: Throwable) {
            handleApiError(t)
        }
    }

    private fun formatDateToServer(dateStr: String?): String {
        if (dateStr.isNullOrEmpty()) return ""

        // 將 YYYY/MM/DD 轉換為 YYYY-MM-DD
        return try {
            val parts = dateStr.split("/")
            if (parts.size == 3) {
                String.format(
                    "%04d-%02d-%02d",
                    parts[0].toInt(),
                    parts[1].toInt(),
                    parts[2].toInt()
                )
            } else {
                dateStr
            }
        } catch (e: Exception) {
            Log.e("DATE_FORMAT", "日期格式轉換錯誤: $dateStr", e)
            dateStr
        }
    }

    // 成功處理邏輯
    private fun handleSuccessfulResponse(request: BrainHealthRequest) {
        hideLoading()
        val surveyData = BrainHealthData(
            name = request.name,
            gender = request.gender,
            birthdate = request.birth,
            phone = request.contactNumber,
            address = request.correspondenceAddress,
            maritalStatus = request.maritalStatus,
            educationLevel = request.education,
            economicStatus = request.economicSituation,
            caseDate = request.caseDate,
            livingWithFamily = if (request.liveTogether) "有" else "無",
            jobStatus = when (request.work) {
                0 -> "無"
                1 -> "全職"
                2 -> "兼職"
                else -> "未知"
            },
            religion = when (request.religion) {
                0 -> "無"
                1 -> "佛教"
                2 -> "道教"
                3 -> "基督教"
                4 -> "天主教"
                5 -> "回教"
                else -> "未知"
            },
            region = when (request.area) {
                1 -> "南投"
                2 -> "草屯"
                3 -> "國姓"
                4 -> "埔里"
                5 -> "竹山"
                6 -> "水里"
                7 -> "大里"
                8 -> "霧峰"
                9 -> "本院"
                else -> "未知"
            },
            q1 = request.questions[0],
            q2 = request.questions[1],
            q3 = request.questions[2],
            q4 = request.questions[3],
            q5 = request.questions[4],
            q6 = request.questions[5],
            q7 = request.questions[6],
            q8 = request.questions[7],
            q9 = request.questions[8],
            q10 = request.questions[9],
            q11 = request.questions[10],
            q12 = request.questions[11],
            q13 = request.questions[12],
            q14 = request.questions[13],
            q15 = request.questions[14]
        )
        saveToViewModels(surveyData) // 保存到 ViewModel
        showToast("問卷提交成功") // 提示用戶
        navigateToResult() // 導航到結果頁面
        // 提交成功後清空問卷數據
        healthViewModel.clearSurvey()
    }

    // 錯誤處理邏輯
    private fun handleApiError(t: Throwable) {
        hideLoading() // 確保在調用時 binding 仍然有效
        if (isAdded && view != null) {
            val errorMessage = when (t) {
                is java.net.UnknownHostException -> "無法連接到伺服器"
                is java.net.SocketTimeoutException -> "連接超時"
                else -> "網路錯誤：${t.message}"
            }
            showError(errorMessage) // 顯示錯誤信息
            Log.e("API_ERROR", errorMessage, t) // 記錄錯誤日誌
        }
    }

    // 數據處理方法
    private fun validateInputs(): Boolean {
        val data = collectSurveyData()
        return data.validate() // 使用 BrainHealthData 的 validate 方法
    }

    // 驗證問卷資料
    private fun isValidSurveyData(data: BrainHealthData): Boolean {
        return !data.name.isNullOrEmpty() &&
                !data.gender.isNullOrEmpty() &&
                !data.birthdate.isNullOrEmpty() &&
                !data.phone.isNullOrEmpty() &&
                !data.address.isNullOrEmpty() &&
                !data.maritalStatus.isNullOrEmpty() &&
                !data.educationLevel.isNullOrEmpty() &&
                !data.economicStatus.isNullOrEmpty() &&
                !data.caseDate.isNullOrEmpty() &&
                !data.livingWithFamily.isNullOrEmpty() &&
                !data.jobStatus.isNullOrEmpty() &&
                !data.religion.isNullOrEmpty() &&
                !data.region.isNullOrEmpty() &&
                binding.question1RadioGroup.checkedRadioButtonId != -1 &&
                binding.question2RadioGroup.checkedRadioButtonId != -1 &&
                binding.question3RadioGroup.checkedRadioButtonId != -1 &&
                binding.question4RadioGroup.checkedRadioButtonId != -1 &&
                binding.question5RadioGroup.checkedRadioButtonId != -1 &&
                binding.question6RadioGroup.checkedRadioButtonId != -1 &&
                binding.question7RadioGroup.checkedRadioButtonId != -1 &&
                binding.question8RadioGroup.checkedRadioButtonId != -1 &&
                binding.question9RadioGroup.checkedRadioButtonId != -1 &&
                binding.question10RadioGroup.checkedRadioButtonId != -1 &&
                binding.question11RadioGroup.checkedRadioButtonId != -1 &&
                binding.question12RadioGroup.checkedRadioButtonId != -1 &&
                binding.question13RadioGroup.checkedRadioButtonId != -1 &&
                binding.question14RadioGroup.checkedRadioButtonId != -1 &&
                binding.question15RadioGroup.checkedRadioButtonId != -1
    }

    // 儲存到 ViewModels
    private fun saveToViewModels(data: BrainHealthData) {
        // 儲存到結果 ViewModel
        val surveyAnswers = mutableMapOf(
            "姓名" to (data.name ?: "未知姓名"),
            "性別" to (data.gender ?: "未知性別"),
            "出生年月日" to (data.birthdate ?: "未知生日"),
            "聯絡電話" to (data.phone ?: "未知電話"),
            "通訊地址" to (data.address ?: "未知地址"),
            "婚姻狀態" to (data.maritalStatus ?: "未知婚姻狀態"),
            "教育程度" to (data.educationLevel ?: "未知教育程度"),
            "經濟狀況" to (data.economicStatus ?: "未知經濟狀況"),
            "收案日期" to (data.caseDate ?: "未知日期"),
            "與家人同住" to (data.livingWithFamily ?: "未知"),
            "工作" to (data.jobStatus ?: "未知"),
            "宗教信仰" to (data.religion ?: "未知"),
            "收案地區" to (data.region ?: "未知"),
            // 加入問題答案
            "1. 我到人多的地方，無法應付那種壓力很大的感覺" to if(data.q1) "是" else "否",
            "2. 我覺得我無法親近別人" to if(data.q2) "是" else "否",
            "3. 我覺得我無法信任別人" to if(data.q3) "是" else "否",
            "4. 我覺得我無法跟別人說話" to if(data.q4) "是" else "否",
            "5. 我覺得我無法跟別人相處" to if(data.q5) "是" else "否",
            "6. 我覺得我無法跟別人合作" to if(data.q6) "是" else "否",
            "7. 我覺得我無法跟別人一起工作" to if(data.q7) "是" else "否",
            "8. 我覺得我無法跟別人一起生活" to if(data.q8) "是" else "否",
            "9. 我覺得我無法跟別人一起玩" to if(data.q9) "是" else "否",
            "10. 我覺得我無法跟別人一起學習" to if(data.q10) "是" else "否",
            "11. 我覺得我無法跟別人一起運動" to if(data.q11) "是" else "否",
            "12. 我覺得我無法跟別人一起吃飯" to if(data.q12) "是" else "否",
            "13. 我覺得我無法跟別人一起看電影" to if(data.q13) "是" else "否",
            "14. 我覺得我無法跟別人一起聊天" to if(data.q14) "是" else "否",
            "15. 我覺得我無法跟別人一起散步" to if(data.q15) "是" else "否"
        )

        // 儲存到 HealthViewModel
        healthViewModel.apply {
            setName(data.name ?: "未知姓名")
            setGender(data.gender ?: "未知性別")
            setBirthdate(data.birthdate ?: "未知生日")
            setPhone(data.phone ?: "未知電話")
            setAddress(data.address ?: "未知地址")
            setMaritalStatus(data.maritalStatus ?: "未知婚姻狀態")
            setEducationLevel(data.educationLevel ?: "未知教育程度")
            setEconomicStatus(data.economicStatus ?: "未知經濟狀況")
            setCaseDate(data.caseDate ?: "未知日期")
            setLivingWithFamily(data.livingWithFamily ?: "未知")
            setJobStatus(data.jobStatus ?: "未知")
            setReligion(data.religion ?: "未知")
            setRegion(data.region ?: "未知")
        }

        // 添加日誌以檢查 surveyAnswers 的內容
        Log.d("BrainHealthFragment", "Setting survey answers in ViewModel: $surveyAnswers")
        resultViewModel.setSurveyAnswers(surveyAnswers) // 保存問卷答案到結果 ViewModel
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) // 檢查網路是否可用
    }

    // 導航到 SurveyResultFragment
    // 在提交問卷後，導航到 SurveyResultFragment
    private fun navigateToResult() {
        Log.d("BrainHealthFragment", "Navigating to SurveyResultFragment")
        findNavController().navigate(R.id.action_brainHealthFragment_to_surveyResultFragment)
    }
}