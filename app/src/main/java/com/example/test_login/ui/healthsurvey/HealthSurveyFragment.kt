package com.example.test_login.ui.healthsurvey

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHealthSurveyBinding

class HealthSurveyFragment : Fragment() {
    private var _binding: FragmentHealthSurveyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HealthSurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthSurveyBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(HealthSurveyViewModel::class.java)

        setupViews()

        return view
    }

    private fun setupViews() {
        val submitButton: Button = binding.submitSurveyButton
        submitButton.setOnClickListener {
            saveSurveyAnswers()
            navigateBack()
        }

        val spinner1: Spinner = binding.answer1Spinner
        setupSpinner(spinner1, R.array.yes_no_options)
        // Setup other Spinners similarly
    }

    private fun setupSpinner(spinner: Spinner, optionsArray: Int) {
        if (optionsArray != 0) {
            ArrayAdapter.createFromResource(
                requireContext(),
                optionsArray,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        } else {
            Log.e("HealthSurveyFragment", "Invalid optionsArray ID: $optionsArray")
        }
    }

    private fun saveSurveyAnswers() {
        val answer1 = binding.answer1Spinner.selectedItem?.toString()
        if (::viewModel.isInitialized) {
            viewModel.addSurveyAnswer(answer1)
            // Save answers for other questions similarly
        } else {
            Log.e("HealthSurveyFragment", "ViewModel not initialized.")
        }
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

