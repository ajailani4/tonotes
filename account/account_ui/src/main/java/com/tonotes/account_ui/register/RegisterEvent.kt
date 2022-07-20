package com.tonotes.account_ui.register

sealed class RegisterEvent {
    object Register : RegisterEvent()
    data class OnUsernameChanged(val username: String) : RegisterEvent()
    data class OnNameChanged(val name: String) : RegisterEvent()
    data class OnPasswordChanged(val password: String) : RegisterEvent()
    object OnPasswordVisibilityChanged : RegisterEvent()
}
