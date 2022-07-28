package com.tonotes.account_ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.account_domain.use_case.LoginAccountUseCase
import com.tonotes.core.domain.use_case.SaveAccessTokenUseCase
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginAccountUseCase: LoginAccountUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : ViewModel() {
    var loginState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisibility by mutableStateOf(false)
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LogIn -> login()

            is LoginEvent.OnUsernameChanged -> username = event.username

            is LoginEvent.OnPasswordChanged -> password = event.password

            is LoginEvent.OnPasswordVisibilityChanged -> passwordVisibility = !passwordVisibility
        }
    }

    private fun login() {
        loginState = UIState.Loading

        viewModelScope.launch {
            val resource = loginAccountUseCase(
                username = username,
                password = password
            )

            resource.catch {
                loginState = UIState.Error(it.localizedMessage)
            }.collect {
                loginState = when (it) {
                    is Resource.Success -> {
                        saveAccessTokenUseCase(it.data?.accessToken!!)
                        UIState.Success(null)
                    }

                    is Resource.Error -> {
                        UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}