package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class VersionModel(
        var dev: String? = null,
        var prod: String? = null)
