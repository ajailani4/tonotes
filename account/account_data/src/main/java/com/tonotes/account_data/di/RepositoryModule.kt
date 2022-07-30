package com.tonotes.account_data.di

import com.tonotes.account_data.repository.AccountRepositoryImpl
import com.tonotes.account_domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository
}