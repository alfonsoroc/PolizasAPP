package com.example.polizasapp.core

import android.content.Context
import javax.inject.Inject

class Prefs @Inject constructor(val context:Context) {
    val SHARED_TOKEN = "Mytoken"
    val SHARED_USE_TOKEN ="token"
    val storage = context.getSharedPreferences(SHARED_TOKEN,0);
    fun saveToken(token:String){
        storage.edit().putString(SHARED_USE_TOKEN,token).apply()
    }
    fun getToken():String{
        return storage.getString(SHARED_USE_TOKEN,"")!!
    }
}