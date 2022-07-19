package com.tonotes.core.di

import com.tonotes.core.data.repository.UserCredentialRepository
import com.tonotes.core.data.repository.impl.UserCredentialRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserCredentialRepository(
        userCredentialRepositoryImpl: UserCredentialRepositoryImpl
    ): UserCredentialRepository
}