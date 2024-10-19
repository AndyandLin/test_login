package com.example.test_login.ui.otherSurveyManage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class SurveyAdapter(
    private val surveyList: List<OtherSurveyManageFragment.SurveyForm>,
    private val onItemClicked: (OtherSurveyManageFragment.SurveyForm) -> Unit
) : RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_survey_form, parent, false)
        return SurveyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val survey = surveyList[position]
        holder.tvSurveyType.text = survey.type
        holder.tvSurveyName.text = survey.name

        holder.itemView.setOnClickListener {
            onItemClicked(survey)
        }
    }

    override fun getItemCount() = surveyList.size

    class SurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSurveyType: TextView = itemView.findViewById(R.id.tv_survey_type)
        val tvSurveyName: TextView = itemView.findViewById(R.id.tv_survey_name)
    }
}