package com.example.forex.data.sharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.forex.data.sharedpreferences.LocalDataStoreConstant.SHARED_PREFERENCE_TAG
import javax.inject.Inject

class SeekerCapitalTestLocalDataStore @Inject constructor(context: Application):
    SharedPreferenceLocalDataStore {
    private val sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE)
    }

    override fun writeFloat(key: String, value: Float) {
        with (sharedPreferences.edit()) {
            putFloat(key, value)
            apply()
        }
    }

    override fun readFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0.0f)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}