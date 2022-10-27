package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TaxiModel(
        var car: String? = null,
        var nameSiteTaxi: String? = null,
        var nameTaxi: String? = null,
        var phoneTaxi: String? = null,
        var schedule: String? = null,
        var photoTaxi: String? = null,
        var key: String? = null,
        var location: String? = null,
        var isAvailableTaxi: Boolean? = null
)

