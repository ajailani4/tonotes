package com.tonotes.account_ui.viewmodel

import com.tonotes.account_domain.model.UserCredential
import com.tonotes.account_domain.use_case.RegisterAccountUseCase
import com.tonotes.account_ui.register.RegisterEvent
import com.tonotes.account_ui.register.RegisterViewModel
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
class RegisterViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    @Mock
    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var onEvent: (RegisterEvent) -> Unit

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(registerAccountUseCase, saveAccessTokenUseCase)
        onEvent = registerViewModel::onEvent
    }

    @Test
    fun `Register should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userCredential))

            doReturn(resource).`when`(registerAccountUseCase)(
                username = anyString(),
                name = anyString(),
                password = anyString()
            )

            onEvent(RegisterEvent.Register)

            val isSuccess = when (registerViewModel.registerState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Register should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserCredential>())

            doReturn(resource).`when`(registerAccountUseCase)(
                username = anyString(),
                name = anyString(),
                password = anyString()
            )

            onEvent(RegisterEvent.Register)

            val isSuccess = when (registerViewModel.registerState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}