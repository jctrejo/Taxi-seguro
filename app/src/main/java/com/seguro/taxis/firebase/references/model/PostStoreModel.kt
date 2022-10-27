package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostStoreModel(var bio: String? = "",
                   var foto: String? = "",
                   var nombre: String? = "",
                   var titulo: String? = "")
