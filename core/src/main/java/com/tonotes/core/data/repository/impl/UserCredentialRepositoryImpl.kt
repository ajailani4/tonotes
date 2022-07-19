package com.tonotes.core.data.repository.impl

import com.tonotes.core.data.PreferencesDataStore
import com.tonotes.core.data.repository.UserCredentialRepository
import javax.inject.Inject

class UserCredentialRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserCredentialRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        preferencesDataStore.saveAccessToken(accessToken)
    }

    override fun getAccessToken() = preferencesDataStore.getAccessToken()
}