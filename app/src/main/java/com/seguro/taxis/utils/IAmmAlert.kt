package com.xolostudios.cemain.ui.custom.alert

import android.content.Context

interface IAmmAlert {
    fun title(title: String): IAmmAlert
    fun message(message: String): IAmmAlert
    fun setActionAccept(title: String, action: () -> Unit): IAmmAlert
    fun setCancelAcction(title: String,action: () -> Unit):IAmmAlert
    fun show(context: Context)
}
