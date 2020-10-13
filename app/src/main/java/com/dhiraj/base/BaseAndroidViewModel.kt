package com.example.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel(app: Application): AndroidViewModel(app) {
    val compositeDisposable = CompositeDisposable()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val message: MutableLiveData<String> = MutableLiveData()

    fun onClear() {
        compositeDisposable.clear()
        super.onCleared()
    }
}