package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityInitialPageBinding

class InitialPageActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityInitialPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInitialPageBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val spinnerMon = findViewById<Spinner>(R.id.InitPageLocationSpinner_SP)
        val itemsMon = arrayOf("Aveiro", "Bragança", "Braga", "Coimbra", "Faro", "Funchal", "Guarda", "Lisboa", "Portalegre", "Porto", "Santarém", "Viana do Castelo")
        val adapterMon = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMon )
        spinnerMon.setAdapter(adapterMon)
    }

    private val editProfileContract by lazy {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Toast.makeText(this@InitialPageActivity,
                    "User updated with success!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}