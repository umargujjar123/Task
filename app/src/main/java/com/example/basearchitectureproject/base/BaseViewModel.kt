package com.example.basearchitectureproject.base


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basearchitectureproject.common.Event

open class BaseViewModel : ViewModel() {

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>>
        get() = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Event<Boolean>>()
    val error: LiveData<Event<Boolean>>
        get() = _error

    private val _closeAction = MutableLiveData<Event<Unit>>()
    val closeAction: LiveData<Event<Unit>>
        get() = _closeAction

    private val _finishAction = MutableLiveData<Event<Unit>>()
    val finishAction: LiveData<Event<Unit>>
        get() = _finishAction

    private val _emptyResult = MutableLiveData<Event<Unit>>()
    val emptyResult: LiveData<Event<Unit>>
        get() = _emptyResult

    fun setLoading(loading: Boolean) {
        _loading.postValue(loading)
    }

    fun setToastMessage(message: String?) {
        message?.let {
            _toastMessage.value = Event(message)
        }
    }


}