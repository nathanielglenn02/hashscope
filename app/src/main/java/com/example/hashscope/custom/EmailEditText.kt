package com.example.hashscope.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        if (hint.isNullOrEmpty()) {
            hint = "Email"
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateEmail(s.toString())
            }
        })
    }

    private fun validateEmail(email: String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error = null
        } else {
            error = "Invalid email format"
        }
    }
}

