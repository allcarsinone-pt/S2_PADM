package com.allcarsinone.allcarsinone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
    }

    fun acioPreferences() {
        val prefs = AcioPreferences("AcioUser", Date(),
            false, false, true, true, true, true
        )
        DataUtils.getSharedPreferences(this).edit()
            .putString("acioPreferences", prefs.toJSONString())
            .apply()

        val preferencesString = DataUtils.getSharedPreferences(this).getString("acioPreferences", null)
        if (preferencesString != null) {
            val prefs2 = AcioPreferences(preferencesString)
            // ou
            val prefs3 = AcioPreferences.convertJSONToPreferences(preferencesString)
        }
    }
}