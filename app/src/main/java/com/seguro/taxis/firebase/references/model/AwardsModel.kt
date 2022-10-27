package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class AwardsModel(
        var points: String? = null,
        var name: String? = null,
        var imageAwards: String? = null,
        var available: Boolean? = null,
        var awardChange: String? = null
)
