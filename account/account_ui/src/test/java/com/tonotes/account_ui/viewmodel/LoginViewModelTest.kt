package com.tonotes.account_ui.viewmodel

import com.tonotes.account_domain.model.UserCredential
import com.tonotes.account_domain.use_case.LoginAccountUseCase
import com.tonotes.account_ui.login.LoginEvent
import com.tonotes.account_ui.login.LoginViewModel
import com.tonotes.account_ui.util.TestCoroutineRule
import com.tonotes.account_ui.util.userCredential
import com.tonotes.core.domain.use_case.SaveAccessTokenUseCase
import com.tonotes.core.util.Resource
import com.tonotes.core.util.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var loginAccountUseCase: LoginAccountUseCase

    @Mock
    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var onEvent: (LoginEvent) -> Unit

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginAccountUseCase, saveAccessTokenUseCase)
        onEvent = loginViewModel::onEvent
    }

    @Test
    fun `Login account should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userCredential))

            doReturn(resource).`when`(loginAccountUseCase)(
                username = anyString(),
                password = anyString()
            )

            onEvent(LoginEvent.LogIn)

            val isSuccess = when (loginViewModel.loginState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Login should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserCredential>())

            doReturn(resource).`when`(loginAccountUseCase)(
                username = anyString(),
                password = anyString()
            )

            onEvent(LoginEvent.LogIn)

            val isSuccess = when (loginViewModel.loginState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}