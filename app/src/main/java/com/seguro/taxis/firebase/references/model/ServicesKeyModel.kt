package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ServicesKeyModel(var key: String? = "",
                            var id: String? = "")