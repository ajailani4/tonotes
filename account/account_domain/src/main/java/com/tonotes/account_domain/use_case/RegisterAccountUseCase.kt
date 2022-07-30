package com.tonotes.account_domain.use_case

import com.tonotes.account_domain.repository.AccountRepository
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        username: String,
        name: String,
        password: String
    ) = accountRepository.register(
        username = username,
        name = name,
        password = password
    )
}