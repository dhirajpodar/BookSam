package com.example.extension

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val TAG = "TAG"
const val BOOKSAM = "BOOKSAM::::"
fun Any.toJsonString(): String {
    return Gson().toJson(this)
}

fun <T> String.toObj(obj: Class<T>): T {
    return Gson().fromJson(this, obj)
}

fun <T> String.toObjList(obj: Class<T>): List<T>? {

    return Gson().fromJson<List<T>>(this, object : TypeToken<T>() {}.type)
}


fun Any.setLog(message: String) {
    Log.d(TAG, BOOKSAM + message)
}