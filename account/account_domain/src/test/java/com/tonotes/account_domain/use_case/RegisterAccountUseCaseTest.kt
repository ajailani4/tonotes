package com.tonotes.account_domain.use_case

import com.tonotes.account_domain.model.UserCredential
import com.tonotes.account_domain.repository.AccountRepositoryFake
import com.tonotes.account_domain.util.ResourceType
import com.tonotes.account_domain.util.userCredential
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterAccountUseCaseTest {
    private lateinit var accountRepositoryFake: AccountRepositoryFake
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    @Before
    fun setUp() {
        accountRepositoryFake = AccountRepositoryFake()
        registerAccountUseCase = RegisterAccountUseCase(accountRepositoryFake)
    }

    @Test
    fun `Register should be success`() = runBlocking {
        accountRepositoryFake.setResourceType(ResourceType.SUCCESS)

        val actualResource = registerAccountUseCase(
            username = "george_z",
            name = "George",
            password = "123"
        ).first()

        assertEquals(
            "Resource should be success",
            Resource.Success(userCredential),
            actualResource
        )
    }

    @Test
    fun `Register should be fail`() = runBlocking {
        accountRepositoryFake.setResourceType(ResourceType.ERROR)

        val actualResource = registerAccountUseCase(
            username = "george_z",
            name = "George",
            password = "123"
        ).first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<UserCredential>(),
            actualResource
        )
    }
}