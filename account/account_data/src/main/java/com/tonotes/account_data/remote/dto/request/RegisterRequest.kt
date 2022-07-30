package com.tonotes.account_data.remote.dto.request

data class RegisterRequest(
    val username: String,
    val name: String,
    val password: String
)
