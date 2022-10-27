package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class RegisterModel(
        var gender: String? = null,
        var birthday: String? = null,
        var userId: String,
        var name: String,
        var email: String,
        var pictureUrl: String
)