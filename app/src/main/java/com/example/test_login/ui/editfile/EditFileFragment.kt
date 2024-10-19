package com.example.test_login.ui.editfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEditFileBinding

class EditFileFragment : Fragment() {

    private var _binding: FragmentEditFileBinding? = null
    private val binding get() = _binding!!
    private lateinit var editFileViewModel: EditFileViewModel
    private lateinit var adapter: FormAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editFileViewModel = ViewModelProvider(this).get(EditFileViewModel::class.java)

        adapter = FormAdapter(
            onEditClick = { formItem ->
                Log.d("EditImageView", "Edit icon clicked for ${formItem.questionId}")
                // 處理編輯操作
            },
            onDeleteClick = { formItem ->
                Log.d("DeleteImageView", "Delete icon clicked for ${formItem.questionId}")
                // 處理刪除操作
            },
            onDownloadClick = { formItem ->
                Log.d("DownloadImageView", "Download icon clicked for ${formItem.questionId}")
                // 處理下載操作
            }
        )

        binding.recyclerViewHealthForms.adapter = adapter
        binding.recyclerViewHealthForms.layoutManager = LinearLayoutManager(context)

        binding.btnAddForm.setOnClickListener {
            findNavController().navigate(R.id.action_editFileFragment_to_otherSurveyFragment)
        }

        binding.btnFillHealthSurvey.setOnClickListener {
            Log.d("EditFile Fragment", "Health Survey button clicked")
            findNavController().navigate(R.id.action_editFileFragment_to_healthSurveyFragment)
        }

        val forms = mutableListOf(
            FormItem("TTPC_SA_20241007080001", "2024-10-07 07:59", "chwu", "編輯", "刪除", "下載"),
            FormItem("TTPC_SA_20241008103614", "2024-10-08 10:35", "chwu", "編輯", "刪除", "下載"),
            FormItem("TTPC_SA_20241014131514", "2024-10-14 13:14", "chwu", "編輯", "刪除", "下載")
        )

        adapter.submitList(forms)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}