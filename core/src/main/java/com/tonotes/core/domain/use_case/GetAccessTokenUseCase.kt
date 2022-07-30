package com.tonotes.core.domain.use_case

import com.tonotes.core.data.repository.UserCredentialRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val userCredentialRepository: UserCredentialRepository
) {
    operator fun invoke() = userCredentialRepository.getAccessToken()
}