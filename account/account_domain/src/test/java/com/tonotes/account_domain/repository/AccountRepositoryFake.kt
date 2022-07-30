package com.tonotes.account_domain.repository

import com.tonotes.account_domain.model.UserCredential
import com.tonotes.account_domain.util.ResourceType
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AccountRepositoryFake : AccountRepository {
    private var userCredential = UserCredential("abcd")

    private lateinit var resourceType: ResourceType

    override fun login(
        username: String,
        password: String
    ): Flow<Resource<UserCredential>> =
        when (resourceType) {
            ResourceType.SUCCESS -> flowOf(Resource.Success(userCredential))

            ResourceType.ERROR -> flowOf(Resource.Error(null))
        }

    override fun register(
        username: String,
        name: String,
        password: String
    ): Flow<Resource<UserCredential>> =
        when (resourceType) {
            ResourceType.SUCCESS -> flowOf(Resource.Success(userCredential))

            ResourceType.ERROR -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}