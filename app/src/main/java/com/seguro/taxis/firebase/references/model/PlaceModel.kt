package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlaceModel(var long: String? = null,
                      var lat: String? = null,
                      var place_name: String? = null,
                      var id: String? = null
)
