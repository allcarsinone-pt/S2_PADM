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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityNotificationsBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)


        notifLoadPreferences()

        viewBinding.NotificationsBackbuttonBtn.setOnClickListener {
            finish()
        }
    }
    fun notifLoadPreferences() {
        val notifySetAllRead = DataUtils.getSharedPreferences(this).getBoolean("notifySetAllRead", false)
        val notifyDeleteOlder = DataUtils.getSharedPreferences(this).getBoolean("notifyDeleteOlder", false)
        val notifyMessages = DataUtils.getSharedPreferences(this).getBoolean("notifyMessages", false)
        val notifyFavorites = DataUtils.getSharedPreferences(this).getBoolean("notifyFavorites", false)

        val notifyEmail =  DataUtils.getSharedPreferences(this).getBoolean("notifyEmail", false)
        val notifyShare = DataUtils.getSharedPreferences(this).getBoolean("notifyShare", false)

        findViewById<Switch>(R.id.Notifications_switch_allNotif).setChecked(notifySetAllRead)
        findViewById<Switch>(R.id.Notifications_switch_older2days).setChecked(notifyDeleteOlder)
        findViewById<Switch>(R.id.Notifications_switch_msgs).setChecked(notifyMessages)
        findViewById<Switch>(R.id.Notifications_switch_favorits).setChecked(notifyFavorites)
        findViewById<Switch>(R.id.Notifications_switch_email).setChecked(notifyEmail)
        findViewById<Switch>(R.id.Notifications_switch_shareFavs).setChecked(notifyShare)
    }


    fun notifSavePreferences(view: View) {
        val notifySetAllRead = findViewById<Switch>(R.id.Notifications_switch_allNotif).isChecked
        val notifyDeleteOlder = findViewById<Switch>(R.id.Notifications_switch_older2days).isChecked
        val notifyMessages = findViewById<Switch>(R.id.Notifications_switch_msgs).isChecked
        val notifyFavorites = findViewById<Switch>(R.id.Notifications_switch_favorits).isChecked
        val notifyEmail =  findViewById<Switch>(R.id.Notifications_switch_email).isChecked
        val notifyShare = findViewById<Switch>(R.id.Notifications_switch_shareFavs).isChecked

        DataUtils.getSharedPreferences(this).edit()
            .putBoolean("notifySetAllRead", notifySetAllRead)
            .putBoolean("notifyDeleteOlder", notifyDeleteOlder)
            .putBoolean("notifyMessages", notifyMessages)
            .putBoolean("notifyFavorites", notifyFavorites)
            .putBoolean("notifyEmail", notifyEmail)
            .putBoolean("notifyShare", notifyShare).
            apply()
        //DataUtils.getSharedPreferences(this).edit()
          //  .putString("acioPreferences", prefs.toJSONString())
            //.apply()

        //Toast.makeText(baseContext, "Preferences successfully saved", Toast.LENGTH_SHORT).show()
    }
}