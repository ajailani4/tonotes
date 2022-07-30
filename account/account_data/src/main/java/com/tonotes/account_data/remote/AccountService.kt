package com.tonotes.account_data.remote

import com.tonotes.account_data.remote.dto.UserCredentialDto
import com.tonotes.account_data.remote.dto.request.LoginRequest
import com.tonotes.account_data.remote.dto.request.RegisterRequest
import com.tonotes.account_data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<UserCredentialDto>>

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<BaseResponse<UserCredentialDto>>
}