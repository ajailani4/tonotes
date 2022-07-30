package com.tonotes.account_domain.repository

import com.tonotes.account_domain.model.UserCredential
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun login(
        username: String,
        password: String
    ): Flow<Resource<UserCredential>>

    fun register(
        username: String,
        name: String,
        password: String
    ): Flow<Resource<UserCredential>>
}