package com.example.forex.data.sharedpreferences

interface SharedPreferenceLocalDataStore {
    fun writeFloat(key:String, value:Float)
    fun readFloat(key:String): Float
    fun clear()
}