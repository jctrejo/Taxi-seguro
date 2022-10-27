package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewAssistanceModel(
    var descriptionService: String? = null,
    var lat: String? = null,
    var long: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var status: String? = null,
    var time: String? = null,
    var price: String? = null,
    var typeAssistence: String? = null,
    var nameDriver: String? = null,
    var location: String? = null,
    var keyDrive: String? = null,
    var phoneDrive: String? = null,
    var imageDriver: String? = null,
    var estimatedDelivery: String? = null,
    var latDriver: String? = null,
    var longDriver: String? = null
)
