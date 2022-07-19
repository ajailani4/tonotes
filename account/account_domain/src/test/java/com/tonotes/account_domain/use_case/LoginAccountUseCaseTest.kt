package com.tonotes.account_domain.use_case

import com.tonotes.account_domain.model.UserCredential
import com.tonotes.account_domain.repository.AccountRepositoryFake
import com.tonotes.account_domain.util.ResourceType
import com.tonotes.account_domain.util.userCredential
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoginAccountUseCaseTest {
    private lateinit var accountRepositoryFake: AccountRepositoryFake
    private lateinit var loginAccountUseCase: LoginAccountUseCase

    @Before
    fun setUp() {
        accountRepositoryFake = AccountRepositoryFake()
        loginAccountUseCase = LoginAccountUseCase(accountRepositoryFake)
    }

    @Test
    fun `Login should return success`() = runBlocking {
        accountRepositoryFake.setResourceType(ResourceType.SUCCESS)

        val actualResource = loginAccountUseCase(
            username = "George",
            password = "123"
        ).first()

        assertEquals(
            "Resource should be success",
            Resource.Success(userCredential),
            actualResource
        )
    }

    @Test
    fun `Login should return fail`() = runBlocking {
        accountRepositoryFake.setResourceType(ResourceType.ERROR)

        val actualResource = loginAccountUseCase(
            username = "George",
            password = "123"
        ).first()

        assertEquals(
            "Resource should be success",
            Resource.Error<UserCredential>(),
            actualResource
        )
    }
}