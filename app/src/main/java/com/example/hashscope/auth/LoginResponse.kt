package com.example.hashscope.auth

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserData
)

data class UserData(
    val email: String,
    val name: String
)

