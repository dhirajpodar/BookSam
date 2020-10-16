package com.dhiraj.booksam.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManger private constructor(val context: Context) {

    private var sharedPreferences =
        context.getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private var preferenceManger: PreferenceManger? = null
        fun getInstance(context: Context): PreferenceManger {
            if (preferenceManger == null) {
                preferenceManger = PreferenceManger(context)
            }

            return preferenceManger!!
        }
    }

    fun saveData(key: String, value: Any) {
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
        }

        editor.apply()
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}