package com.example.hashscope.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentLoginBinding

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
            val username = binding.usernameInput.toString()
            val password = binding.passwordInput.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Pindah ke CategoryFragment setelah login berhasil
                findNavController().navigate(R.id.action_loginFragment_to_categoryFragment)
            } else {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
        // Navigasi ke RegisterFragment
        binding.signupLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
