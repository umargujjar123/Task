package com.example.basearchitectureproject.person.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.basearchitectureproject.base.BaseViewModel
import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.di.CoroutinesDispatcherProvider
import com.example.basearchitectureproject.person.usecases.UploadPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import g5.consultency.cuitalibilam.base.util.Event
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class AddPersonViewModel @Inject constructor(
    private val uploadPersonUseCase: UploadPersonUseCase,
    private val coroutineDispatcherProvider: CoroutinesDispatcherProvider,
) :
    BaseViewModel() {
    private val coroutineScope = CoroutineScope(coroutineDispatcherProvider.main)

    // Ui Models mutators
    val userDetails: UserDetailsModel =
        UserDetailsModel(
            email = "",
            userName = "",
            userMobileNumber = "",
        )


    // Actions
//    private val _addPersonAction = MutableLiveData<Unit>()
//    val addPersonAction: LiveData<Unit> = _addPersonAction

    private val _backButton = MutableLiveData<Event<Unit>>()
    val backBtn: LiveData<Event<Unit>> = _backButton


    private val _uploadPersonMLD: MutableLiveData<UserDetailsModel> = MutableLiveData()
    val uploadPersonLD = Transformations.switchMap(_uploadPersonMLD) {
        uploadPersonUseCase(it).asLiveData()
    }

    val uploadPersonHandlerLD = uploadPersonLD.map {
        when (it.status) {
            Resource.Status.LOADING -> {
                setLoading(true)
            }
            else -> {
                setLoading(false)
            }
        }
        it
    }

    fun addPersonAction() {
        Log.e("TAG", "AddPersonViewModel --> addPersonAction: called")
        _uploadPersonMLD.value = userDetails

    }

    fun backBtn() {
        _backButton.value = Event(Unit)
    }
}