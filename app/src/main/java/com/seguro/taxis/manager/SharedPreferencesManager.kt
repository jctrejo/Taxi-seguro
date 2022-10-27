package com.seguro.taxis.manager

import android.content.Context

class SharedPreferencesManager(context: Context) {

    private val PREFERENCE_CURRENTUSER = "currentUser"
    private val currentUser =
        context.getSharedPreferences(PREFERENCE_CURRENTUSER, Context.MODE_PRIVATE)

    private val PREFERENCE_SIGNEDIN = "signedin"
    private val signedIn = context.getSharedPreferences(PREFERENCE_SIGNEDIN, Context.MODE_PRIVATE)

    private val PREFERENCE_WEB = "web"
    private val pref_web = context.getSharedPreferences(PREFERENCE_WEB, Context.MODE_PRIVATE)

    private val PREFERENCE_LOCATION_GPS = "gps"
    private val pref_location_gps =
        context.getSharedPreferences(PREFERENCE_LOCATION_GPS, Context.MODE_PRIVATE)

    private val PREFERENCE_PROFILE_IMAGE = "profile_image"
    private val pref_profile_image =
        context.getSharedPreferences(PREFERENCE_PROFILE_IMAGE, Context.MODE_PRIVATE)

    private val PREFERENCE_CURRENT_ASSISTENCE = "currentAssistence"
    private val currentAssistence =
        context.getSharedPreferences(PREFERENCE_CURRENT_ASSISTENCE, Context.MODE_PRIVATE)

    private val PREFERENCE_KEY_USER = "keyUser"
    private val currentUserKey =
        context.getSharedPreferences(PREFERENCE_KEY_USER, Context.MODE_PRIVATE)

    private val PREFERENCE_IS_NEW_FACEBOOK = "isNewFacebook"
    private val prefIsNewFacebook =
        context.getSharedPreferences(PREFERENCE_IS_NEW_FACEBOOK, Context.MODE_PRIVATE)

    private val PREFERENCE_VERSION = "version"
    private val pref_version =
        context.getSharedPreferences(PREFERENCE_VERSION, Context.MODE_PRIVATE)

    private val PREFERENCE_CURRENTUSER_EMAIL = "currentUserEmail"
    private val currentUserEmail =
        context.getSharedPreferences(PREFERENCE_CURRENTUSER_EMAIL, Context.MODE_PRIVATE)

    private val PREFERENCE_CURRENTU_USER_NAME = "user_name"
    private val currentUserName =
        context.getSharedPreferences(PREFERENCE_CURRENTU_USER_NAME, Context.MODE_PRIVATE)

    private val PREFERENCE_CURRENT_TIME_ASSITANCE = "currentTimeAssistance"
    private val currentTimeAssistance =
        context.getSharedPreferences(PREFERENCE_CURRENT_TIME_ASSITANCE, Context.MODE_PRIVATE)

    fun setSignedIn(signedInValue: Boolean) {
        val editor = signedIn.edit();
        editor.putBoolean(PREFERENCE_SIGNEDIN, signedInValue)
        editor.apply()
    }

    fun getSignedIn(): Boolean {
        return signedIn.getBoolean(PREFERENCE_SIGNEDIN, false)
    }

    fun setWebStore(web: String) {
        val editor = pref_web.edit()
        editor.putString(PREFERENCE_WEB, web)
        editor.apply()
    }

    fun getWeStore(): String {
        return pref_web.getString(PREFERENCE_WEB, "") ?: ""
    }

    fun setLocationGPS(GPS: String) {
        val editor = pref_location_gps.edit()
        editor.putString(PREFERENCE_LOCATION_GPS, GPS)
        editor.apply()
    }

    fun getLocationGPS(): String {
        return pref_location_gps.getString(PREFERENCE_LOCATION_GPS, "") ?: ""
    }

    fun setProfileImage(profileImage: String) {
        val editor = pref_profile_image.edit()
        editor.putString(PREFERENCE_PROFILE_IMAGE, profileImage)
        editor.apply()
    }

    fun getProfileImage(): String {
        return pref_profile_image.getString(PREFERENCE_PROFILE_IMAGE, "") ?: ""
    }

    fun setAssistence(asistence: String) {
        val editor = currentAssistence.edit();
        editor.putString(PREFERENCE_CURRENT_ASSISTENCE, asistence)
        editor.apply()
    }

    fun getAssistence(): String {
        return currentAssistence.getString(PREFERENCE_CURRENT_ASSISTENCE, "") ?: ""
    }

    fun setKeyUser(keyFacebook: String) {
        val editor = currentUserKey.edit();
        editor.putString(PREFERENCE_KEY_USER, keyFacebook)
        editor.apply()
    }

    fun getKeyUser(): String {
        return currentUserKey.getString(PREFERENCE_KEY_USER, "") ?: ""
    }

    fun setIsNewFacebook(isFacebook: String) {
        val editor = prefIsNewFacebook.edit()
        editor.putString(PREFERENCE_IS_NEW_FACEBOOK, isFacebook)
        editor.apply()
    }

    fun getIsNewFacebook(): String {
        return prefIsNewFacebook.getString(PREFERENCE_IS_NEW_FACEBOOK, "") ?: ""
    }

    fun setVersion(version: String) {
        val editor = pref_version.edit()
        editor.putString(PREFERENCE_VERSION, version)
        editor.apply()
    }

    fun getVersion(): String {
        return pref_version.getString(PREFERENCE_VERSION, "") ?: ""
    }

    fun setCurrentEmailUser(currentUserEmailJson: String) {
        val editor = currentUserEmail.edit();
        editor.putString(PREFERENCE_CURRENTUSER_EMAIL, currentUserEmailJson)
        editor.apply()
    }

    fun getCurrentEmailUser(): String {
        return currentUserEmail.getString(PREFERENCE_CURRENTUSER_EMAIL, "") ?: ""
    }

    fun setUserName(key: String) {
        val editor = currentUserName.edit();
        editor.putString(PREFERENCE_CURRENTU_USER_NAME, key)
        editor.apply()
    }

    fun getUserName(): String {
        return currentUserName.getString(PREFERENCE_CURRENTU_USER_NAME, "") ?: ""
    }

    fun setCurrentTimeAssistance(isCurrentTime: Boolean) {
        val editor = currentTimeAssistance.edit();
        editor.putBoolean(PREFERENCE_CURRENT_TIME_ASSITANCE, isCurrentTime)
        editor.apply()
    }

    fun getCurrentTimeAssistance(): Boolean {
        return currentTimeAssistance.getBoolean(PREFERENCE_CURRENT_TIME_ASSITANCE, false)
    }
}
