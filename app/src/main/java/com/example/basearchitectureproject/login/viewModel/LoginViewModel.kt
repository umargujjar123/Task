package com.example.basearchitectureproject.login.viewModel

import androidx.lifecycle.*


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import com.example.basearchitectureproject.login.model.LoginDataModel
import com.example.basearchitectureproject.login.model.LoginModel
import com.example.basearchitectureproject.login.usecase.LoginUseCase
import com.example.basearchitectureproject.util.Resource

@HiltViewModel
@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(
    private val loginLoginUseCase: LoginUseCase
) : ViewModel() {

    var loginDataModel = LoginDataModel()
    private var loginAction: MutableLiveData<Unit> = MutableLiveData()

    private val _loginUiModel = Transformations.switchMap(loginAction) {
        loginLoginUseCase(loginDataModel).asLiveData()
    }
    val loinUiModel: LiveData<Resource<LoginModel?>> = _loginUiModel

    fun btnLogin() {
        loginAction.value = Unit
    }


}