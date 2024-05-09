package com.allcarsinone.allcarsinone

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        val spinnerMon = findViewById<Spinner>(R.id.Payment_Exp_Mon_Spinner_ET)
        val itemsMon = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        val adapterMon = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMon )
        spinnerMon.setAdapter(adapterMon)

        val spinnerYear = findViewById<Spinner>(R.id.Payment_Exp_Year_Spinner_ET)
        val itemsYear = arrayOf("2024", "2025", "2026", "2027")
        val adapterYear = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsYear )
        spinnerYear.setAdapter(adapterYear)
    }
}