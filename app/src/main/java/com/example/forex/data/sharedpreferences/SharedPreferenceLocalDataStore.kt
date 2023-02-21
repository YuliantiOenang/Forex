package com.example.forex.data.sharedpreferences

import com.example.forex.core.datastore.ChangeListVersions

interface SharedPreferenceLocalDataStore {
    fun writeFloat(key:String, value:Float)
    fun readFloat(key:String): Float
    fun getChangeListVersions(): ChangeListVersions

    fun updateChangeListVersion(update:  ChangeListVersions.() -> ChangeListVersions)
    fun clear()
}