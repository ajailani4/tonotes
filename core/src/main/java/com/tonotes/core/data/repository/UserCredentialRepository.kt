package com.tonotes.core.data.repository

import kotlinx.coroutines.flow.Flow


interface UserCredentialRepository {
    suspend fun saveAccessToken(accessToken: String)

    fun getAccessToken(): Flow<String>
}