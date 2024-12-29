package com.app.springbootsecuritydb.model

data class LoginResponse(
    val token: String,
    val user: User
)