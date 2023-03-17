package com.example.basearchitectureproject.login.repositry

import com.example.basearchitectureproject.base.BaseDataSource
import com.example.basearchitectureproject.login.model.LoginModel
import com.example.basearchitectureproject.login.newtwork.LoginApi
import com.example.basearchitectureproject.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RepoRepository {
    fun getRepoList(
        email: String,
        password: String,
        deviceToken: String
    ): Flow<Resource<LoginModel>>

}

class UserRepository @Inject constructor(
    private val providerLoginApi: LoginApi
) : BaseDataSource(), RepoRepository {


    override fun getRepoList(
        email: String,
        password: String,
        deviceToken: String
    ): Flow<Resource<LoginModel>> = flow {
        emit(Resource.loading(null))
        val response = safeApiCall {
            providerLoginApi.loginUser(email, password, deviceToken)

        }
        emit(response)

    }

}



