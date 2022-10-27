package com.seguro.taxis.manager

import com.google.firebase.database.FirebaseDatabase

class Constants {
    companion object {

        private const val V1 = "V1"
        private const val USERS_APP = "users_app"
        private const val LOCATIONS = "locations"
        private const val VERSION = "version"
        private const val ANDROID = "android"
        private const val ASSISTANCE = "assistance"
        private const val MANDADITOS = "mandadito"
        private const val ASSISTS_FINISH = "assists_finish"
        private const val ASSISTS_TYPE = "assists_type"

        val BaseFirebaseReference = FirebaseDatabase.getInstance().reference.child(V1)
        val Users = BaseFirebaseReference.child(USERS_APP)
        val version = BaseFirebaseReference.child(VERSION).child(ANDROID)
        val assistance = BaseFirebaseReference.child(MANDADITOS).child(ASSISTS_TYPE)
        val firebaseAssistanceKey = BaseFirebaseReference.child(MANDADITOS).child(ASSISTANCE)
        val firebaseFinishAssistanceKey = BaseFirebaseReference.child(MANDADITOS).child(ASSISTS_FINISH)
        val firebaseLocationKey = BaseFirebaseReference.child(LOCATIONS)

        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        const val DOUBLE_POINTS = ":"
        const val EMPTY = ""
        const val TRUE_STR = "true"
        const val FALSE_STR = "false"

        //Assistence
        const val IM_HERE = "Aquí Estoy"
        const val ON_MY_WAY = "En camino"
        const val NO_ASSIGNED = "No asignado"
        const val CONFIRMATION = "confirmado"
        const val ACCEPT = "aceptado"
        const val ARRIVED = "arrivado"
        const val CANCEL = "cancelar"
        const val EVENT_HOME = "HOME"
        const val EVENT_SERVICES = "SERVICES"
        const val EVENTE_SECTION = "SECTION"

        //TODO: Phone
        const val PHONE_SUPPORT = "7861343261"

        //TODO: ASSISTANCE
        const val ASSISTANCE_MOTORCYCLE_DELIVERIES = "Moto Entregas"

        //TODO: LOCATION
        const val SELECT_STATE = "Elige tu estado"

        //Int
        const val SPLASH_SCREEN_DELAY: Long = 5000
        const val PLACE_PICKER_REQUEST = 1
        const val CHANNEL_ID = "12345"
    }
}
