package com.example.hashscope.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentRegisterBinding
import com.example.hashscope.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var loadingView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLoadingIndicator()

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (validateInput(username, email, password)) {
                val registerRequest = RegisterRequest(username, email, password)
                showLoading()
                registerUser(registerRequest)
            }
        }

        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        RetrofitClient.apiService.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                hideLoading()
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null && registerResponse.success) {
                        Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), registerResponse?.message ?: "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                hideLoading()
                Toast.makeText(requireContext(), "Registration Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateInput(username: String, email: String, password: String): Boolean {
        if (username.isEmpty()) {
            binding.usernameInput.error = "Username is required"
            return false
        }

        if (email.isEmpty()) {
            binding.emailInput.error = "Email is required"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "Invalid email format"
            return false
        }

        if (password.isEmpty()) {
            binding.passwordInput.error = "Password is required"
            return false
        } else if (password.length < 8) {
            binding.passwordInput.error = "Password must be at least 8 characters"
            return false
        }

        return true
    }

    private fun addLoadingIndicator() {
        val inflater = LayoutInflater.from(requireContext())
        loadingView = inflater.inflate(R.layout.loading_indicator, binding.root, false)
        binding.root.addView(loadingView)
        val logo = loadingView?.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo?.startAnimation(rotateAnimation)
        loadingView?.visibility = View.GONE
    }

    private fun showLoading() {
        loadingView?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
