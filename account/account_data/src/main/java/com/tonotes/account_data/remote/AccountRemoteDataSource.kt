package com.tonotes.account_data.remote

import com.tonotes.account_data.remote.dto.LoginRequest
import com.tonotes.account_data.remote.dto.RegisterRequest
import javax.inject.Inject

class AccountRemoteDataSource @Inject constructor(
    private val accountService: AccountService
) {
    suspend fun login(loginRequest: LoginRequest) = accountService.login(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) = accountService.register(registerRequest)
}