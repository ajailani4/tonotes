package com.tonotes.account_data.repository

import com.tonotes.core_ui.R
import android.content.Context
import com.tonotes.account_data.remote.AccountRemoteDataSource
import com.tonotes.account_data.remote.dto.request.LoginRequest
import com.tonotes.account_data.remote.dto.request.RegisterRequest
import com.tonotes.core.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    @ApplicationContext private val context: Context
) : AccountRepository {
    override fun login(loginRequest: LoginRequest) =
        flow {
            val response = accountRemoteDataSource.login(loginRequest)

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data))

                401 -> emit(Resource.Error(context.getString(R.string.incorrect_username_or_pass)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun register(registerRequest: RegisterRequest) =
        flow {
            val response = accountRemoteDataSource.register(registerRequest)

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data))

                409 -> emit(Resource.Error(context.getString(R.string.username_already_exists)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}