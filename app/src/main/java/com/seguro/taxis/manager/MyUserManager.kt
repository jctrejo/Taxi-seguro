package com.seguro.taxis.manager

import android.content.Context
import com.google.gson.Gson
import com.seguro.taxis.models.UserEmailModel

class MyUserManager private constructor() {
    init {
        println("This ($this) is a singleton")
    }

    private object Holder {
        val INSTANCE = MyUserManager()
    }

    companion object {
        val instance: MyUserManager by lazy { Holder.INSTANCE }
    }

    var context: Context? = null

    fun signedIn(value: Boolean) {
        context?.applicationContext?.let { SharedPreferencesManager(it).setSignedIn(value) }
    }

    fun isSignedIn(): Boolean? {
        return context?.applicationContext?.let { SharedPreferencesManager(it).getSignedIn() }
    }

    fun storeUser(user: UserEmailModel) {
        val json = Gson().toJson(user)
        context?.applicationContext?.let { SharedPreferencesManager(it).setCurrentEmailUser(json) }
    }

    fun getCurrentUser(): UserEmailModel {
        val json = context?.applicationContext?.let { SharedPreferencesManager(it).getCurrentEmailUser() }
        return Gson().fromJson(json, UserEmailModel::class.java)
    }
}