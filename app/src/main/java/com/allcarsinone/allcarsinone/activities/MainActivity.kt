package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.allcarsinone.allcarsinone.AcioPreferences
import com.allcarsinone.allcarsinone.DataUtils
import com.allcarsinone.allcarsinone.database.VehiclesDatabase
import com.allcarsinone.allcarsinone.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        // Test ROOM database
        ////////////////////////////////////////////////
        val db = Room.databaseBuilder( applicationContext, VehiclesDatabase::class.java, "vehicles.db").build()
        lifecycleScope.launch {

            val data = withContext(Dispatchers.IO) {db.vehiclesDao().getAll() }
            data?.forEach {
                println(it)
            }
        }
        ////////////////////////////////////////////////

        acioPreferences()
    }
    private fun acioPreferences() {
        val preferencesString = DataUtils.getSharedPreferences(this)
            .getString("acioPreferences", null)
        if (preferencesString != null) {
            val prefs = AcioPreferences(preferencesString)

            //TODO: Disponibilizar estas variáveis para uso da aplicação

            Log.d("userName", prefs.userName)
            Log.d("installDate", prefs.installDate.toString())
            Log.d("notifySetAllRead", prefs.notifySetAllRead.toString())
            Log.d("notifyDeleteOlder", prefs.notifyDeleteOlder.toString())
            Log.d("notifyMessages", prefs.notifyMessages.toString())
            Log.d("notifyFavorites", prefs.notifyFavorites.toString())
            Log.d("notifyEmail", prefs.notifyEmail.toString())
            Log.d("notifyShare", prefs.notifyShare.toString())
        }
    }
}