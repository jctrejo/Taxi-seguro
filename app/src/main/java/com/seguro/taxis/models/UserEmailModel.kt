package com.seguro.taxis.models

import org.json.JSONObject
import java.io.Serializable

class UserEmailModel : Serializable {

    var facebookId: String
    var name: String
    var pictureUrl: String

    constructor(json: JSONObject) {
        facebookId = json.getString("id") ?: ""
        name = json.getString("name") ?: ""

        pictureUrl = json.getJSONObject("picture").getJSONObject("data").getString("url")
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
                "facebookId" to facebookId,
                "name" to name,
                "email" to "",
                "pictureUrl" to pictureUrl)
    }
}