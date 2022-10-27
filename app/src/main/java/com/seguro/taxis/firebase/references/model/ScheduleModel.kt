package com.seguro.taxis.firebase.references.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ScheduleModel(var domingoDesp: String? = null,
                         var domingoMatu: String? = null,
                         var sabadoDesp: String? = null,
                         var sabadoMatu: String? = null,
                         var semanaDesp: String? = null,
                         var semanaMatu: String? = null)
