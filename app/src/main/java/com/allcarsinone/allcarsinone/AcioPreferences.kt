package com.allcarsinone.allcarsinone

import org.json.JSONObject
import java.util.Date

class AcioPreferences(var userName: String = "", var installDate: Date = Date(),
                      var notifySetAllRead: Boolean = false, var notifyDeleteOlder: Boolean = false,
                      var notifyMessages: Boolean = false, var notifyFavorites: Boolean = false,
                      var notifyEmail: Boolean = false, var notifyShare: Boolean = false ) {

    fun toJSONString(): String {
        var jsonObj = JSONObject()
        jsonObj.put("username", this.userName)
        jsonObj.put("birthdate", this.installDate.time)
        jsonObj.put("notifySetAllRead", this.notifySetAllRead)
        jsonObj.put("notifyDeleteOlder", this.notifyDeleteOlder)
        jsonObj.put("notifyMessages", this.notifyMessages)
        jsonObj.put("notifyFavorites", this.notifyFavorites)
        jsonObj.put("notifyEmail", this.notifyEmail)
        jsonObj.put("notifyShare", this.notifyShare)
        return jsonObj.toString()
    }

    constructor(str: String) : this() {
        val jsonObj = JSONObject(str)
        this.userName = jsonObj.getString("username")
        this.installDate = Date(jsonObj.getLong("installDate"))
        this.notifySetAllRead = jsonObj.getBoolean("notifySetAllRead")
        this.notifyDeleteOlder = jsonObj.getBoolean("notifyDeleteOlder")
        this.notifyMessages = jsonObj.getBoolean("notifyMessages")
        this.notifyFavorites = jsonObj.getBoolean("notifyFavorites")
        this.notifyEmail = jsonObj.getBoolean("notifyEmail")
        this.notifyShare = jsonObj.getBoolean("notifyShare")
    }

    companion object {
        fun convertJSONToStudent(json: String): AcioPreferences {
            val jsonObj = JSONObject(json)
            val prefs = AcioPreferences()
            prefs.userName = jsonObj.getString("username")
            prefs.installDate = Date(jsonObj.getLong("installDate"))
            prefs.notifySetAllRead = jsonObj.getBoolean("notifySetAllRead")
            prefs.notifyDeleteOlder = jsonObj.getBoolean("notifyDeleteOlder")
            prefs.notifyMessages = jsonObj.getBoolean("notifyMessages")
            prefs.notifyFavorites = jsonObj.getBoolean("notifyFavorites")
            prefs.notifyEmail = jsonObj.getBoolean("notifyEmail")
            prefs.notifyShare = jsonObj.getBoolean("notifyShare")
            return prefs
        }
    }
}