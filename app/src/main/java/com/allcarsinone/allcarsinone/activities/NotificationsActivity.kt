package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.allcarsinone.allcarsinone.AcioPreferences
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityLoginBinding
import com.allcarsinone.allcarsinone.databinding.ActivityNotificationsBinding
import com.allcarsinone.allcarsinone.databinding.ActivityNotificationsViewBinding
import com.github.mikephil.charting.charts.BarChart
import org.json.JSONObject
import java.util.Date

class NotificationsActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityNotificationsBinding
    private lateinit var prefs: AcioPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityNotificationsBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val preferencesString = DataUtils.getSharedPreferences(this).getString("acioPreferences", null)
        if (preferencesString != null) {
            this.prefs = AcioPreferences.convertJSONToPreferences(preferencesString)
        }
        notifLoadPreferences()

        viewBinding.NotificationsBackbuttonBtn.setOnClickListener {
            finish()
        }
    }
    fun notifLoadPreferences() {
        findViewById<Switch>(R.id.Notifications_switch_allNotif).setChecked(prefs.notifySetAllRead)
        findViewById<Switch>(R.id.Notifications_switch_older2days).setChecked(prefs.notifyDeleteOlder)
        findViewById<Switch>(R.id.Notifications_switch_msgs).setChecked(prefs.notifyMessages)
        findViewById<Switch>(R.id.Notifications_switch_favorits).setChecked(prefs.notifyFavorites)
        findViewById<Switch>(R.id.Notifications_switch_email).setChecked(prefs.notifyEmail)
        findViewById<Switch>(R.id.Notifications_switch_shareFavs).setChecked(prefs.notifyShare)
    }
    fun notifSavePreferences(view: View) {
        prefs.notifySetAllRead = findViewById<Switch>(R.id.Notifications_switch_allNotif).isChecked
        prefs.notifyDeleteOlder = findViewById<Switch>(R.id.Notifications_switch_older2days).isChecked
        prefs.notifyMessages = findViewById<Switch>(R.id.Notifications_switch_msgs).isChecked
        prefs.notifyFavorites = findViewById<Switch>(R.id.Notifications_switch_favorits).isChecked
        prefs.notifyEmail =  findViewById<Switch>(R.id.Notifications_switch_email).isChecked
        prefs.notifyShare = findViewById<Switch>(R.id.Notifications_switch_shareFavs).isChecked

        DataUtils.getSharedPreferences(this).edit()
            .putString("acioPreferences", prefs.toJSONString())
            .apply()

        Toast.makeText(baseContext, "Preferences successfully saved", Toast.LENGTH_SHORT).show()
    }
}