package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationGPSModel(var location: String? = null)

data class LocationModel(var location: String? = null)