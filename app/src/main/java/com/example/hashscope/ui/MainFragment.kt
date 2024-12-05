package com.example.hashscope.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentMainBinding
import com.example.hashscope.ui.DetailTopicActivity

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Spinner options
        val platforms = resources.getStringArray(R.array.platform_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, platforms)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.platformSpinner.adapter = adapter

        // Setup Spinner selection listener
        binding.platformSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPlatform = parent?.getItemAtPosition(position).toString()

                // Navigate to DetailTopicActivity if a valid item is selected
                if (position != 0) { // Ignore the first item ("Select platform")
                    navigateToDetailTopic(selectedPlatform)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun navigateToDetailTopic(platform: String) {
        val intent = Intent(requireContext(), DetailTopicActivity::class.java)
        intent.putExtra("SELECTED_PLATFORM", platform)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        // Reset spinner to the default "Select platform" option
        binding.platformSpinner.setSelection(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
