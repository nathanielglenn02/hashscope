package com.example.hashscope.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

