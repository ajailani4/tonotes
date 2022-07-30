package com.tonotes.account_domain.use_case

import com.tonotes.account_domain.repository.AccountRepository
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        username: String,
        password: String
    ) = accountRepository.login(
        username = username,
        password = password
    )
}