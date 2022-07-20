package com.tonotes.account_domain.use_case

import com.tonotes.account_data.remote.dto.RegisterRequest
import com.tonotes.account_data.repository.AccountRepository
import com.tonotes.account_domain.mapper.toUserCredential
import com.tonotes.account_domain.model.UserCredential
import com.tonotes.core.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        username: String,
        name: String,
        password: String
    ) =
        flow<Resource<UserCredential>> {
            accountRepository.register(
                RegisterRequest(
                    username = username,
                    name = name,
                    password = password
                )
            ).collect {
                when (it) {
                    is Resource.Success -> emit(Resource.Success(it.data?.toUserCredential()))

                    is Resource.Error -> emit(Resource.Error(it.message))
                }
            }
        }
}