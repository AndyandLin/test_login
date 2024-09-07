package com.example.test_login.ui.editfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEditFileBinding

class EditFileFragment : Fragment() {

    private var _binding: FragmentEditFileBinding? = null
    private val binding get() = _binding!!
    private lateinit var editFileViewModel: EditFileViewModel
    private lateinit var recyclerViewForms: RecyclerView
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

        recyclerViewForms = binding.recyclerViewHealthForms
        adapter = FormAdapter()
        recyclerViewForms.adapter = adapter
        recyclerViewForms.layoutManager = LinearLayoutManager(context)

        binding.btnFillHealthSurvey.setOnClickListener {
            Log.d("EditFileFragment", "Health Survey button clicked")
            navigateToHealthSurvey()
        }

        val forms = mutableListOf(
            FormItem("SA", "PUB_SA_10000", "PUB", "Nov. 30, 2023 1:36 p.m."),
            FormItem("SA", "PUB_SA_10001", "PUB", "Nov. 30, 2023 1:36 p.m.")
        )
        adapter.submitList(forms)
    }

    private fun navigateToHealthSurvey() {
        Log.d("EditFileFragment", "Navigating to Health Survey")
        try {
            findNavController().navigate(R.id.action_editFileFragment_to_healthSurveyFragment)
        } catch (e: Exception) {
            Log.e("EditFileFragment", "Navigation failed", e)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
