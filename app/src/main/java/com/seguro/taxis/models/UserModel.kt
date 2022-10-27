package com.seguro.taxis.models

import org.json.JSONObject
import java.io.Serializable

class UserModel : Serializable {

    var facebookId: String
    var name: String
    var email: String
    var pictureUrl: String

    constructor(json: JSONObject) {
        facebookId = json.getString("id") ?: ""
        name = json.getString("name") ?: ""
        email = json.getString("email") ?: ""

        pictureUrl = json.getJSONObject("picture").getJSONObject("data").getString("url")
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
                "facebookId" to facebookId,
                "name" to name,
                "email" to email,
                "pictureUrl" to pictureUrl)
    }
}