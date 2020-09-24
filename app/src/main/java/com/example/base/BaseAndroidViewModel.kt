package com.example.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseAndroidViewModel(app: Application): AndroidViewModel(app) {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val message: MutableLiveData<String> = MutableLiveData()

    fun onClear() {
        super.onCleared()
    }
}