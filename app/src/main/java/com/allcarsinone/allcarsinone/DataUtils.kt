package com.allcarsinone.allcarsinone

import android.content.Context
import android.content.SharedPreferences

class DataUtils {
    companion object {
        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(
                context.resources.getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        }
    }
}