package com.example.test_login.ui.otherSurveyManage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


class OtherSurveyManageFragment : Fragment() {

    data class SurveyForm(val type: String, val name: String)

    private val surveyList = listOf(
        SurveyForm("初訪", "初次評估單-病情病症評估"),
        SurveyForm("轉介", "疑似精神病人個案轉介單"),
        SurveyForm("轉介", "社區高風險精神病人轉介單"),
        SurveyForm("轉介", "資源轉介單"),
        SurveyForm("續訪", "社區高風險精神病人「續訪」訪視紀錄單"),
        SurveyForm("續訪", "居家護理紀錄單張"),
        SurveyForm("續訪", "高風險病人出院後追蹤訪視"),
        SurveyForm("結案", "社區高風險精神病人「結案」訪視紀錄單"),
        SurveyForm("結案", "結案紀錄單")
    )

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_other_survey_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_other_surveys)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SurveyAdapter(surveyList) { surveyForm ->
            navigateToSurveyDetail(surveyForm)
        }
    }

    // 點擊後導航至相應的 Fragment
    private fun navigateToSurveyDetail(surveyForm: SurveyForm) {
        val navController = findNavController()
        Log.d("OtherSurveyManageFragment", "Navigating to: ${surveyForm.name}") // 添加日志记录
        when (surveyForm.name) {
            "初次評估單-病情病症評估" -> {
                val action = OtherSurveyManageFragmentDirections
                    .actionOtherSurveyManageFragmentToFirstEvaluationFragment()
                navController.navigate(action)
            }
            "結案紀錄單" -> {
                val action = OtherSurveyManageFragmentDirections
                    .actionOtherSurveyManageFragmentToClosingAgreementFragment()
                navController.navigate(action)
            }
            // 其他問卷導航邏輯可以依次添加
            // "其他問卷名稱" -> {
            //     val action = OtherSurvteyManageFragmentDirections
            //         .actionOtherSurveyManageFragmentToXXXFragment()
            //     navController.navigate(action)
            // }
            else -> {
                Log.e("OtherSurveyManageFragment", "No navigation action defined for: ${surveyForm.name}") // 添加错误日志
                // 預設處理或提示錯誤
            }
        }
    }
}
