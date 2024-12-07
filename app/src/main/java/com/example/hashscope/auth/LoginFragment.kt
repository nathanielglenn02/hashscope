package com.example.hashscope.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentLoginBinding
import com.example.hashscope.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)

                // Memanggil API login secara asinkron menggunakan Coroutine
                lifecycleScope.launch {
                    try {
                        val response: Response<LoginResponse> = RetrofitClient.apiService.login(loginRequest)

                        if (response.isSuccessful) {
                            // Jika login sukses, ambil token dan simpan
                            val token = response.body()?.token
                            if (!token.isNullOrEmpty()) {
                                // Simpan token ke SharedPreferences atau gunakan sesuai kebutuhan
                                Toast.makeText(requireContext(), "Login Sukses", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_categoryFragment)
                            } else {
                                Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Login Gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Menangani error jika ada masalah dengan API
                        Toast.makeText(requireContext(), "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signupLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
