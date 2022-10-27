package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GasStationModel(var gasAddress: String? = null,
                           var gasLat: String? = null,
                           var gasName: String? = null,
                           var gasPhone: String? = null,
                           var gasSchedule: String? = null,
                           var gasSiteWeb: String? = null,
                           var gaslong: String? = null,
                           var key: String? = null,
                           var location: String? = null
)
