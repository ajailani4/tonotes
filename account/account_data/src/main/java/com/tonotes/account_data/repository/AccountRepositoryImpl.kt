package com.tonotes.account_data.repository

import android.content.Context
import com.tonotes.account_data.mapper.toUserCredential
import com.tonotes.account_data.remote.AccountRemoteDataSource
import com.tonotes.account_data.remote.dto.request.LoginRequest
import com.tonotes.account_data.remote.dto.request.RegisterRequest
import com.tonotes.account_domain.repository.AccountRepository
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    @ApplicationContext private val context: Context
) : AccountRepository {
    override fun login(
        username: String,
        password: String
    ) =
        flow {
            val response = accountRemoteDataSource.login(
                LoginRequest(
                    username = username,
                    password = password
                )
            )

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toUserCredential()))

                401 -> emit(Resource.Error(context.getString(R.string.incorrect_username_or_pass)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun register(
        username: String,
        name: String,
        password: String
    ) =
        flow {
            val response = accountRemoteDataSource.register(
                RegisterRequest(
                    username = username,
                    name = name,
                    password = password
                )
            )

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data?.toUserCredential()))

                409 -> emit(Resource.Error(context.getString(R.string.username_already_exists)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}