package com.example.test_login.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.NumberPicker
import android.widget.RadioGroup
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.myapplication.databinding.FragmentFirstEvaluationBinding

class FirstEvaluationFragment : Fragment() {

    private var _binding: FragmentFirstEvaluationBinding? = null
    private val binding get() = _binding!!

    private lateinit var inputHospitalizationCount: TextInputEditText
    private lateinit var hospitalizationLayout: TextInputLayout
    private lateinit var radioGroup: RadioGroup
    private lateinit var editTextNoMedical: TextInputEditText
    private lateinit var editTextMedicalInstitution: TextInputEditText

    // 新增的變數
    private lateinit var thoughtsLayout: LinearLayout
    private lateinit var checkDelusions: CheckBox
    private lateinit var checkIdeas: CheckBox
    private lateinit var layoutDelusions: LinearLayout
    private lateinit var layoutIdeas: LinearLayout
    private lateinit var editTextDelusions: EditText
    private lateinit var editTextIdeas: EditText
    private lateinit var checkDelusionOther: CheckBox
    private lateinit var checkIdeaOther: CheckBox
    private lateinit var checkThoughtsOthers: CheckBox
    private lateinit var editTextOthers: EditText

    // 新增的變數
    private lateinit var radioGroupReferralOptions: RadioGroup
    private lateinit var radioGroupNoneed: RadioGroup


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstEvaluationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            inputHospitalizationCount = view.findViewById(R.id.input_hospitalization_count)
            hospitalizationLayout = view.findViewById(R.id.hospitalization_layout)
        } catch (e: ClassCastException) {
            Log.e("FirstEvaluationFragment", "ClassCastException: ${e.message}")
        }

        radioGroup = view.findViewById(R.id.radioGroupMedicalHistory)
        editTextNoMedical = view.findViewById(R.id.editTextNoMedical)
        editTextMedicalInstitution = view.findViewById(R.id.editTextMedicalInstitution)

        // 初始化下拉式選單
        setupExposedDropdownMenu(binding.inputEmotion, R.array.emotion_status_options_byfirst)
        setupExposedDropdownMenu(binding.spinnerEducation, R.array.education_level_byfirst)
        setupExposedDropdownMenu(binding.spinnerLivingSituation, R.array.living_situation_options_byfirst)
        setupExposedDropdownMenu(binding.spinnerEmploymentStatus, R.array.employment_status_options_byfirst)
        setupExposedDropdownMenu(binding.spinnerMaritalStatus, R.array.marital_status_options_byfirst)
        setupExposedDropdownMenu(binding.spinnerRelationship, R.array.relationship_options_byfirst)
        setupExposedDropdownMenu(binding.spinnerDanger, R.array.score5_byfirst)
        setupExposedDropdownMenu(binding.spinnerSupport, R.array.score5_byfirst)
        setupExposedDropdownMenu(binding.spinnerCollaboration, R.array.score5_byfirst)
        setupExposedDropdownMenu(binding.spinnerPositive, R.array.score3_byfirst)
        setupExposedDropdownMenu(binding.spinnerDegenerate, R.array.score3_byfirst)
        setupExposedDropdownMenu(binding.spinnerSelfHarm, R.array.score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerViolence, R.array.score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerInterpersonal, R.array.score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerWork, R.array.score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerTimeAllocation, R.array.score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerFamilyLife, R.array.score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerGeneralFunction, R.array.PSP_score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerInterpersonalRelationships, R.array.PSP_score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerSelfCareAbility, R.array.PSP_score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerDisruptiveBehavior, R.array.PSP_score6_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion1, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion2, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion3, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion4, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion5, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion6, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion7, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion8, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion9, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion10, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion11, R.array.CBI_score4_byfirst)
        setupExposedDropdownMenu(binding.spinnerQuestion12, R.array.CBI_score4_byfirst)

        // 初始化「思考、妄想」選項
        thoughtsLayout = view.findViewById(R.id.layout_thoughts)
        checkDelusions = view.findViewById(R.id.check_thoughts_delusions)
        checkIdeas = view.findViewById(R.id.check_thoughts_ideas)
        layoutDelusions = view.findViewById(R.id.layout_delusions)
        layoutIdeas = view.findViewById(R.id.layout_ideas)

        // 初始化「思考、妄想」輸入框
        editTextDelusions = view.findViewById(R.id.editTextDelusions)
        editTextIdeas = view.findViewById(R.id.editTextIdeas)

        // 初始化「思考、妄想」複選框(其他)
        checkDelusionOther = view.findViewById(R.id.check_delusion_other)
        checkIdeaOther = view.findViewById(R.id.check_idea_other)
        checkThoughtsOthers = view.findViewById(R.id.check_thoughts_others)

        // 設置勾選事件
        setupThoughtsCheckboxes()

        hospitalizationLayout.setEndIconOnClickListener {
            showNumberPickerDialog(inputHospitalizationCount)
        }

        setupRadioGroup()
        setupReferralOptions()

        // 設置提交按鈕的點擊事件
        binding.btnSubmit.setOnClickListener {
            if (validateInputs()) {
                submitData()
                navigateToOtherSurveyManage()
            }
        }
    }

    private fun validateInputs(): Boolean {
        // 在這裡添加驗證邏輯
        // 檢查所有必填字段是否已填寫
        // 如果所有必填字段都已填寫，返回 true，否則返回 false
        return true
    }

    private fun submitData() {
        // 在這裡添加數據提交邏輯
        // 可以將數據保存到數據庫或發送到服務器
        // 目前只是顯示一個提示訊息
        Toast.makeText(requireContext(), "數據已提交", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToOtherSurveyManage() {
        // 使用 NavController 導航到 navigation_other_survey_manage
        findNavController().navigate(R.id.action_firstEvaluationFragment_to_navigation_other_survey_manage)
    }

    private fun setupExposedDropdownMenu(autoCompleteTextView: AutoCompleteTextView, arrayResId: Int) {
        val options = resources.getStringArray(arrayResId)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, options)
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setupThoughtsCheckboxes() {
        checkDelusions.setOnCheckedChangeListener { _, isChecked ->
            layoutDelusions.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        checkIdeas.setOnCheckedChangeListener { _, isChecked ->
            layoutIdeas.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun showNumberPickerDialog(editText: TextInputEditText) {
        val currentValue = editText.text.toString().toIntOrNull() ?: 1
        val numberPicker = NumberPicker(requireContext()).apply {
            minValue = 1
            maxValue = 500
            value = currentValue
        }

        AlertDialog.Builder(requireContext())
            .setTitle("選擇住院次數")
            .setView(numberPicker)
            .setPositiveButton("確定") { _, _ ->
                editText.setText(numberPicker.value.toString())
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioNoMedical -> {
                    editTextNoMedical.visibility = View.VISIBLE
                    editTextMedicalInstitution.visibility = View.GONE
                }
                R.id.radioYesMedical -> {
                    editTextNoMedical.visibility = View.GONE
                    editTextMedicalInstitution.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupReferralOptions() {
        radioGroupReferralOptions = view?.findViewById(R.id.radioGroupReferralOptions) ?: return
        radioGroupNoneed = view?.findViewById(R.id.radioGroupNoneed) ?: return
        val radioReferralMedicalInstitution = view?.findViewById<RadioButton>(R.id.radioReferralMedicalInstitution)
        val radioNoNeed = view?.findViewById<RadioButton>(R.id.radioNoNeed)

        radioReferralMedicalInstitution?.setOnCheckedChangeListener { _, isChecked ->
            radioGroupReferralOptions.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        radioNoNeed?.setOnCheckedChangeListener { _, isChecked ->
            radioGroupNoneed.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}