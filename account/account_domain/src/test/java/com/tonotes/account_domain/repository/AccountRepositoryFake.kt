package com.tonotes.account_domain.repository

import com.tonotes.account_data.remote.dto.LoginRequest
import com.tonotes.account_data.remote.dto.UserCredentialDto
import com.tonotes.account_data.repository.AccountRepository
import com.tonotes.account_domain.util.ResourceType
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AccountRepositoryFake : AccountRepository {
    private var userCredentialDto = UserCredentialDto(
        username = "George",
        accessToken = "abcd"
    )

    private lateinit var resourceType: ResourceType

    override fun login(loginRequest: LoginRequest): Flow<Resource<UserCredentialDto>> =
        when (resourceType) {
            ResourceType.SUCCESS -> flowOf(Resource.Success(userCredentialDto))

            ResourceType.ERROR -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}