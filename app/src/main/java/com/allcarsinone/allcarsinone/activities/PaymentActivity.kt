package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageStandBinding
import com.allcarsinone.allcarsinone.databinding.ActivityPaymentBinding
import com.allcarsinone.allcarsinone.databinding.ActivityStandStatisticsBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val spinnerMon = findViewById<Spinner>(R.id.Payment_Exp_Mon_Spinner_ET)
        val itemsMon = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        val adapterMon = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMon )
        spinnerMon.setAdapter(adapterMon)

        val spinnerYear = findViewById<Spinner>(R.id.Payment_Exp_Year_Spinner_ET)
        val itemsYear = arrayOf("2024", "2025", "2026", "2027")
        val adapterYear = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsYear )
        spinnerYear.setAdapter(adapterYear)

        viewBinding.NotificationsBackbuttonBtn.setOnClickListener {
            finish()
        }
    }
}