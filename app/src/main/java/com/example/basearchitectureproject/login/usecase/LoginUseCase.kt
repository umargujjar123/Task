package com.example.basearchitectureproject.login.usecase


import android.util.Log
import android.util.Patterns
import com.example.basearchitectureproject.login.model.LoginDataModel
import com.example.basearchitectureproject.login.model.LoginModel
import com.example.basearchitectureproject.login.repositry.RepoRepository
import com.example.basearchitectureproject.util.FlowUseCase
import com.example.basearchitectureproject.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class LoginUseCase constructor(
    private val repoRepository: RepoRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO

) : FlowUseCase<LoginDataModel, Resource<LoginModel?>>(dispatcher) {
    private var errorMessage: String = ""


    override fun execute(parameters: LoginDataModel): Flow<Resource<LoginModel?>> = flow {
        if (validateLoginData(parameters)) {

            repoRepository.getRepoList(
                parameters.email,
                parameters.password,
                "123"
            ).collect {
                emit(it)
            }
        } else {

            emit(Resource.error(errorMessage, null))
        }
    }

    private fun validateLoginData(loginDataModel: LoginDataModel): Boolean {
        Log.e(
            "LoginViewModel",
            "validateLoginData: ${loginDataModel.email}  password ${loginDataModel.password}"
        )

        if (loginDataModel.email.isNotEmpty() && loginDataModel.password.isNotEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(loginDataModel.email.trim()).matches()) {
                errorMessage = "Email Pattern is not Valid"
                return false
            }
            return true
        } else {
            errorMessage = "Provide Email and password"
            return false
        }
    }
}