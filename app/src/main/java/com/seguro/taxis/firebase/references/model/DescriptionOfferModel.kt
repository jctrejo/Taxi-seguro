package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DescriptionOfferModel(var descriptionOffers: String? = null,
                                 var price: String? = null,
                                 var terms: String? = null,
                                 var key: String? = null,
                                 var nameStoreCoupon: String? = null,
                                 var dateCoupon: String? = null,
                                 var couponExpire: String? = null,
                                 var activeCoupon: Boolean? = null,
                                 var urlImageStr: String? = null
)
