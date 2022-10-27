package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var name: String? = null,
                var email: String? = null,
                var phoneNumber: String? = null,
                var location: String? = null,
                var pictureUrl: String? = null,
                var storeType: String? = null,
                var userType: Int? = null,
                var activeUser: Boolean? = null,
                var idMembership: String? = null,
                var membershipExpire: String? = null,
                var membershipCreation: String? = null,
                var isAvailableTaxi: String? = null,
                var prizeShop: Boolean? = null,
                var availableMotorcycleDeliveries: Boolean? = null
)