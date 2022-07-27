package com.tonotes.account_data.repository

import com.tonotes.core_ui.R
import android.content.Context
import com.tonotes.account_data.remote.AccountRemoteDataSource
import com.tonotes.account_data.remote.dto.response.BaseResponse
import com.tonotes.account_data.remote.dto.request.LoginRequest
import com.tonotes.account_data.remote.dto.request.RegisterRequest
import com.tonotes.account_data.remote.dto.UserCredentialDto
import com.tonotes.account_data.util.userCredentialDto
import com.tonotes.account_domain.repository.AccountRepository
import com.tonotes.core.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AccountRepositoryTest {

    @Mock
    private lateinit var accountRemoteDataSource: AccountRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var accountRepository: AccountRepository

    @Before
    fun setUp() {
        accountRepository = AccountRepositoryImpl(
            accountRemoteDataSource,
            context
        )
    }

    @Test
    fun `Login should return success`() = runBlocking {
        val response = Response.success(
            200,
            BaseResponse(
                code = 200,
                status = "OK",
                data = userCredentialDto
            )
        )

        doReturn(response).`when`(accountRemoteDataSource).login(any())

        val actualResource = accountRepository.login(
            LoginRequest(
                username = "george_z",
                password = "123"
            )
        ).first()

        assertEquals(
            "Resource should be success",
            Resource.Success(userCredentialDto),
            actualResource
        )
    }

    @Test
    fun `Login should return fail`() = runBlocking {
        val response: Response<Unit> = Response.error(
            401,
            ResponseBody.create(null, "")
        )

        doReturn(null).`when`(context).getString(R.string.incorrect_username_or_pass)
        doReturn(response).`when`(accountRemoteDataSource).login(any())

        val actualResource = accountRepository.login(
            LoginRequest(
                username = "george_z",
                password = "123"
            )
        ).first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<UserCredentialDto>(),
            actualResource
        )
    }

    @Test
    fun `Register should return success`() = runBlocking {
        val response = Response.success(
            201,
            BaseResponse(
                code = 201,
                status = "Created",
                data = userCredentialDto
            )
        )

        doReturn(response).`when`(accountRemoteDataSource).register(any())

        val actualResource = accountRepository.register(
            RegisterRequest(
                username = "george_z",
                name = "George",
                password = "123"
            )
        ).first()

        assertEquals(
            "Resource should be success",
            Resource.Success(userCredentialDto),
            actualResource
        )
    }

    @Test
    fun `Register should return fail`() = runBlocking {
        val response: Response<Unit> = Response.error(
            409,
            ResponseBody.create(null, "")
        )

        doReturn(null).`when`(context).getString(R.string.username_already_exists)
        doReturn(response).`when`(accountRemoteDataSource).register(any())

        val actualResource = accountRepository.register(
            RegisterRequest(
                username = "george_z",
                name = "George",
                password = "123"
            )
        ).first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<UserCredentialDto>(),
            actualResource
        )
    }
}