package com.allcarsinone.allcarsinone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.activities.LoginActivity
import com.allcarsinone.allcarsinone.models.UserPayload
import com.auth0.android.jwt.JWT

class AuthUtils {

    companion object {

        fun setUserPreferences(context: Context, token: String? ,username: String, roleid: Int) {
            val sharedPreferences = DataUtils.getSharedPreferences(context)
            sharedPreferences.edit().putString("token", token!!).apply()
            sharedPreferences.edit().putString("username", username).apply()
            sharedPreferences.edit().putInt("role",roleid).apply()
        }
        fun logoutUser(context: Context) {
            setUserPreferences(context, "", "", 0)
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
        fun loginUser(context: Context, username: String, role: Int) {
            val sharedPreferences = DataUtils.getSharedPreferences(context)
            sharedPreferences.edit().putInt("role", role).apply()

            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
        fun getRole(context: Context): Int {
            val sharedPrefs = DataUtils.getSharedPreferences(context)
            val role = sharedPrefs.getInt("role", 0)
            return role
        }

        fun validateToken(context: Context, token: String?): UserPayload {

            var isExpired = false
            try {
                val jwt = JWT(token!!)
                isExpired = jwt.isExpired(0)
                val username = jwt.getClaim("username").asString()!!
                val roleid = jwt.getClaim("role_id").asInt()!!
                setUserPreferences(context, token ,username, roleid)
                return UserPayload((username.isNotEmpty() && roleid > 0), username, roleid)
            }
            catch(ex: Exception) {

                if(!token.isNullOrEmpty() || isExpired) {
                    logoutUser(context)
                }
                return UserPayload(false, "", 0)
            }

        }

    }
}