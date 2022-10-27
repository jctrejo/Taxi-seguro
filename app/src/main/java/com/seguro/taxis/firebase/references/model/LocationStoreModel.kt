package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationStoreModel(var address: String? = null,
                              var name: String? = null)