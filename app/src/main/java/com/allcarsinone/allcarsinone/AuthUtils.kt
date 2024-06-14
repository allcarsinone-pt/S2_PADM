package com.allcarsinone.allcarsinone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.activities.LoginActivity

class AuthUtils {

    companion object {
        fun logoutUser(context: Context) {
            val sharedPreferences = DataUtils.getSharedPreferences(context)
            sharedPreferences.edit().putString("token", "").apply()

            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
        fun loginUser(context: Context, role: Int) {
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
    }
}