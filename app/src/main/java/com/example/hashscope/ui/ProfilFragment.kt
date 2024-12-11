package com.example.hashscope.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hashscope.MainActivity
import com.example.hashscope.databinding.FragmentProfilBinding
import com.example.hashscope.preference.UserPreference

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        val userPreference = UserPreference(requireContext())

        // Set data user ke UI
        binding.textUserName.text = userPreference.getUserName() ?: "Nama tidak tersedia"
        binding.textUserEmail.text = userPreference.getUserEmail() ?: "Email tidak tersedia"

        // Handle logout
        binding.buttonLogout.setOnClickListener {
            // Hapus token
            userPreference.clearToken()

            // Arahkan kembali ke MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
