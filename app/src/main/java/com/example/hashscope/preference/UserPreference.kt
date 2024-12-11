package com.example.hashscope.preference

import android.content.Context

class UserPreference(context: Context) {

    // Inisialisasi SharedPreferences
    private val preferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    // Simpan token
    fun saveToken(token: String) {
        preferences.edit().putString("token", token).apply()
    }

    // Ambil token
    fun getToken(): String? {
        return preferences.getString("token", null)
    }

    // Hapus token
    fun clearToken() {
        preferences.edit().remove("token").apply()
    }

    // (Opsional) Simpan nama user
    fun saveUserName(name: String) {
        preferences.edit().putString("user_name", name).apply()
    }

    // Ambil nama user
    fun getUserName(): String? {
        return preferences.getString("user_name", null)
    }

    // (Opsional) Simpan email user
    fun saveUserEmail(email: String) {
        preferences.edit().putString("user_email", email).apply()
    }

    // Ambil email user
    fun getUserEmail(): String? {
        return preferences.getString("user_email", null)
    }
}
