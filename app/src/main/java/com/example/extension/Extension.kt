package com.example.extension

import com.google.gson.Gson

fun Any.toJsonString(): String {
    return Gson().toJson(this)
}

fun <T> String.toJsonObj(obj: Class<T>): T {
    return Gson().fromJson(this, obj)
}