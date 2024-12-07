package com.example.hashscope.custom

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.hashscope.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val toggleButton: ImageView
    private var isPasswordVisible = false

    init {
        if (hint.isNullOrEmpty()) {
            hint = "Password" // Set default hint hanya jika tidak didefinisikan di XML
        }
        // Set input type untuk password (disembunyikan secara default)
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Setup toggle visibility button
        toggleButton = ImageView(context).apply {
            setImageResource(R.drawable.ic_visibility_off)
            setPadding(16, 0, 16, 0)
            setOnClickListener { togglePasswordVisibility() }
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, toggleButton.drawable, null)

        // Tambahkan validasi untuk password
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validatePassword(s.toString())
            }
        })
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            toggleButton.setImageResource(R.drawable.ic_visibility_off)
        } else {
            // Show password
            inputType = InputType.TYPE_CLASS_TEXT
            toggleButton.setImageResource(R.drawable.ic_visibility_on)
        }
        isPasswordVisible = !isPasswordVisible
        setSelection(text?.length ?: 0) // Pindahkan kursor ke akhir teks
    }

    private fun validatePassword(password: String) {
        // Validasi password minimal 8 karakter dan mengandung angka
        if (password.length >= 8 && password.any { it.isDigit() }) {
            error = null
        } else {
            error = "Password must be at least 8 characters and contain a number"
        }
    }
}