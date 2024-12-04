package com.example.hashscope.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle Register Button click
        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.toString()
            val password = binding.passwordInput.toString()

            if (validateInput(username, email, password)) {
                // Add registration logic here (API call, etc.)
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        // Handle "Already have an account? Login" link
        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun validateInput(username: String, email: String, password: String): Boolean {
        // Validate username
        if (username.isEmpty()) {
            binding.usernameInput.error = "Username is required"
            return false
        }

        // Validate email
        if (email.isEmpty()) {
            binding.emailInput.error = "Email is required"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "Invalid email format"
            return false
        }

        // Validate password
        if (password.isEmpty()) {
            binding.passwordInput.error = "Password is required"
            return false
        } else if (password.length < 8) {
            binding.passwordInput.error = "Password must be at least 8 characters"
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
