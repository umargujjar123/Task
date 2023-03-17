package com.example.basearchitectureproject.person.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.basearchitectureproject.base.BaseViewModel
import com.example.basearchitectureproject.common.Event
import com.example.basearchitectureproject.data.UserDetailsModel
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.person.usecases.PersonsUseCase
import com.example.basearchitectureproject.person.usecases.SyncRemoteDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val personsUseCase: PersonsUseCase,
    private val personRepository: PersonRepository,
    private val syncRemoteDataUseCase: SyncRemoteDataUseCase
) : BaseViewModel() {

    val personsResource: LiveData<Resource<List<UserDetailsModel>>> =
        personsUseCase(Unit).asLiveData()
    var persons = Transformations.map(personsResource) {
        when (it.status) {
            Resource.Status.LOADING -> {
                setLoading(true)
            }
            else -> {
                setLoading(false)
            }
        }
        it.data
    }

    //Actions
    private val _addPersonAction = MutableLiveData<Event<Unit>>()
    val addPersonAction: LiveData<Event<Unit>> = _addPersonAction

    private val _syncDataFromRemoteServerMLD: MutableLiveData<Event<Unit>> = MutableLiveData()

    private val remoteSyncLD = Transformations.switchMap(_syncDataFromRemoteServerMLD) {
        syncRemoteDataUseCase(Unit).asLiveData()
    }

    var remoteSyncStateLD = Transformations.map(remoteSyncLD) {
        when (it.status) {
            Resource.Status.LOADING -> {
                setLoading(true)
            }
            else -> {
                setLoading(false)
            }
        }
        it.data
    }


    //Actions
//    private val _personDetailAction = MutableLiveData<Event<UserDetailsModel>>()
//    val personDetailAction: LiveData<Event<UserDetailsModel>> = _personDetailAction

//
//    fun personClickListener(userDetailsModel: UserDetailsModel) {
//        _personDetailAction.value = Event(userDetailsModel)
//    }


    fun actionAddPerson() {
        _addPersonAction.value = Event(Unit)
    }

    fun actionSyncData() {
        _syncDataFromRemoteServerMLD.value = Event(Unit)
    }


}