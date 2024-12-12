package com.example.hashscope.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentLoginBinding
import com.example.hashscope.preference.UserPreference
import com.example.hashscope.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference
    private var loadingView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreference = UserPreference(requireContext())
        addLoadingIndicator()
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showLoading()

            lifecycleScope.launch {
                try {
                    val loginRequest = LoginRequest(email, password)
                    val response: Response<LoginResponse> = RetrofitClient.apiService.login(loginRequest)

                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val token = loginResponse.token
                            val user = loginResponse.user
                            userPreference.saveToken(token)
                            userPreference.saveUserEmail(user.email)
                            userPreference.saveUserName(user.name)

                            Toast.makeText(requireContext(), "Login Sukses", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_categoryFragment)
                        } else {
                            Toast.makeText(requireContext(), "Login gagal: Respons kosong", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Login Gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    hideLoading()
                }
            }
        }

        binding.signupLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
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
