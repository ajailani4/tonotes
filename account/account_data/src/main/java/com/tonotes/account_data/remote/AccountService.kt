package com.tonotes.account_data.remote

import com.tonotes.account_data.remote.dto.BaseResponse
import com.tonotes.account_data.remote.dto.LoginRequest
import com.tonotes.account_data.remote.dto.UserCredentialDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<UserCredentialDto>>
}