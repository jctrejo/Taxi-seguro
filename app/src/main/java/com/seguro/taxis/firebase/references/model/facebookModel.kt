package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class facebookModel(
        var email: String? = null,
        var facebookId: String? = null,
        var name: String? = null,
        var pictureUrl: String? = null,
        var birthday: String? = null,
        var gender: String? = null)
