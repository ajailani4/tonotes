package com.tonotes.account_ui.login

sealed class LoginEvent {
    object LogIn : LoginEvent()
    data class OnUsernameChanged(val username: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    object OnPasswordVisibilityChanged : LoginEvent()
}
