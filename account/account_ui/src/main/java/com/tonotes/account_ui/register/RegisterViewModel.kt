package com.tonotes.account_ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.account_domain.use_case.RegisterAccountUseCase
import com.tonotes.core.domain.use_case.SaveAccessTokenUseCase
import com.tonotes.core.util.Resource
import com.tonotes.core.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : ViewModel() {
    var registerState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var username by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisibility by mutableStateOf(false)
        private set

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register()

            is RegisterEvent.OnUsernameChanged -> username = event.username

            is RegisterEvent.OnNameChanged -> name = event.name

            is RegisterEvent.OnPasswordChanged -> password = event.password

            is RegisterEvent.OnPasswordVisibilityChanged -> passwordVisibility = !passwordVisibility
        }
    }

    private fun register() {
        registerState = UIState.Loading

        viewModelScope.launch {
            val resource = registerAccountUseCase(
                username = username,
                name = name,
                password = password
            )

            resource.catch {
                registerState = UIState.Error(it.localizedMessage)
            }.collect {
                registerState = when (it) {
                    is Resource.Success -> {
                        saveAccessTokenUseCase(it.data?.accessToken!!)
                        UIState.Success(null)
                    }

                    is Resource.Error -> {
                        UIState.Error(it.message)
                    }
                }
            }
        }
    }
}