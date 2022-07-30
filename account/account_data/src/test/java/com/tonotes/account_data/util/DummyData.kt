package com.tonotes.account_data.util

import com.tonotes.account_data.remote.dto.UserCredentialDto
import com.tonotes.account_domain.model.UserCredential

val userCredentialDto = UserCredentialDto(
    username = "test",
    accessToken = "abcd"
)
val userCredential = UserCredential("abcd")