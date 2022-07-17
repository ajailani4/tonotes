package com.tonotes.core.domain.use_case

import com.tonotes.core.data.repository.UserCredentialRepository
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(
    private val userCredentialRepository: UserCredentialRepository
) {
    suspend operator fun invoke(accessToken: String) {
        userCredentialRepository.saveAccessToken(accessToken)
    }
}