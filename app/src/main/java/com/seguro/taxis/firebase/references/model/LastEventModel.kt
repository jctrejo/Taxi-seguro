package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LastEventModel(var dateEvent: String? = "",
                          var addressEvent: String? = "",
                          var descriptionLastEvent: String? = "",
                          var discount: String? = "",
                          var validCoupon: String? = "",
                          var latEvent: String? = "",
                          var longEvent: String? = "",
                          var nameStore: String? = "",
                          var phoneEvent: String? = "",
                          var key: String? = "",
                          var expireEvento: String? = "",
                          var urlImageStr: String? = ""
)
