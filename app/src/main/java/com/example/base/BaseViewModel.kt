package com.dhiraj.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val message: MutableLiveData<String> = MutableLiveData()

    fun onClear() {
        super.onCleared()
    }
}
