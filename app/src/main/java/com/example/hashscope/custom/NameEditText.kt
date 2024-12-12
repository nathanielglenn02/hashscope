package com.example.hashscope.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class NameEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        if (hint.isNullOrEmpty()) {
            hint = "Name"
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateName(s.toString())
            }
        })
    }

    private fun validateName(name: String) {
        if (name.isEmpty()) {
            error = "Name is required"
        } else if (!name.matches(Regex("^[a-zA-Z\\s]+\$"))) {
            error = "Name must contain only letters"
        } else {
            error = null
        }
    }
}
