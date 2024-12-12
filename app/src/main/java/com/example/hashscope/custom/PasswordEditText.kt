package com.example.hashscope.custom

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import com.example.hashscope.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val toggleButton: ImageView
    private var isPasswordVisible = false

    init {
        if (hint.isNullOrEmpty()) {
            hint = "Password"
        }
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        toggleButton = ImageView(context).apply {
            setImageResource(R.drawable.ic_visibility_off)
            setPadding(16, 0, 16, 0)
            setOnClickListener { togglePasswordVisibility() }
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, toggleButton.drawable, null)
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
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            toggleButton.setImageResource(R.drawable.ic_visibility_off)
        } else {
            inputType = InputType.TYPE_CLASS_TEXT
            toggleButton.setImageResource(R.drawable.ic_visibility_on)
        }
        isPasswordVisible = !isPasswordVisible
        setSelection(text?.length ?: 0)
    }

    private fun validatePassword(password: String) {
        if (password.length >= 8 && password.any { it.isDigit() }) {
            error = null
        } else {
            error = "Password must be at least 8 characters and contain a number"
        }
    }
}