package com.tonotes.account_data.repository

import com.tonotes.account_data.remote.dto.LoginRequest
import com.tonotes.account_data.remote.dto.UserCredentialDto
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun login(loginRequest: LoginRequest): Flow<Resource<UserCredentialDto>>
}