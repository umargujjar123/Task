package com.example.basearchitectureproject.user_details.viewmodel

import androidx.lifecycle.*
import com.example.basearchitectureproject.base.BaseViewModel
import com.example.basearchitectureproject.user_details.usecase.GetAllUserDetailsUsesCase
import com.example.basearchitectureproject.user_details.usecase.UploadUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import g5.consultency.cuitalibilam.base.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadUserDetailViewModel @Inject constructor(
    private val uploadUserDetailsUseCase: UploadUserDetailsUseCase,
    private val getAllUserDetailsUsesCase: GetAllUserDetailsUsesCase
) : BaseViewModel() {


    //Actions
    private val _rootClick = MutableLiveData<Event<Unit>>()
    val rootClick: LiveData<Event<Unit>> = _rootClick

    private val _backButton = MutableLiveData<Event<Unit>>()
    val backBtn: LiveData<Event<Unit>> = _backButton

    private val _fetchUserDetails: MutableLiveData<String> = MutableLiveData(
        ""
    )

    val userDetails = Transformations.switchMap(_fetchUserDetails) {
        getAllUserDetailsUsesCase(it).asLiveData()
    }

//    var uploadUserDetailsModel = Transformations.map(userDetails) {
//        UploadUserDetailsModel(userDetails = it.data ?: UserDetailsModel(
//                email = "",
//                userName = "",
//                userMobileNumber = "",
//            )
//        )
//    }
//    private val _inituploadDetailMLD: MutableLiveData<UploadUserDetailsModel> = MutableLiveData()

//    val initUploadDetailLD = Transformations.switchMap(_inituploadDetailMLD) {
//        uploadUserDetailsUseCase(it).asLiveData()
//
//    }
//
//    fun uploadUserDetails() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val userDetails = uploadUserDetailsModel.value
//            _inituploadDetailMLD.postValue(userDetails)
//        }
//    }

    fun backBtn() {
        _backButton.value = Event(Unit)
    }

    fun rootClicked() {
        _rootClick.value = Event(Unit)
    }
}

