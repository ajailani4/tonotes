package com.tonotes.account_data.mapper

import com.tonotes.account_data.remote.dto.UserCredentialDto
import com.tonotes.account_domain.model.UserCredential

fun UserCredentialDto.toUserCredential() =
    UserCredential(this.accessToken)