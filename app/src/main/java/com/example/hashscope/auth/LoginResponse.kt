package com.example.hashscope.auth

data class LoginResponse(
    val message: String,
    val token: String? // Token yang diterima setelah login berhasil
)

