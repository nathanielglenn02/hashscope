package com.example.hashscope.preference

import android.content.Context

class UserPreference(context: Context) {

    private val preferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        preferences.edit().putString("token", token).apply()
    }
    fun getToken(): String? {
        return preferences.getString("token", null)
    }
    fun clearToken() {
        preferences.edit().remove("token").apply()
    }
    fun saveUserName(name: String) {
        preferences.edit().putString("user_name", name).apply()
    }
    fun getUserName(): String? {
        return preferences.getString("user_name", null)
    }
    fun saveUserEmail(email: String) {
        preferences.edit().putString("user_email", email).apply()
    }
    fun getUserEmail(): String? {
        return preferences.getString("user_email", null)
    }
}
