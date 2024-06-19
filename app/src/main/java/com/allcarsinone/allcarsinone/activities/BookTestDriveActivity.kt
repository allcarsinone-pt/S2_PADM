package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityBookTestDriveBinding
import com.allcarsinone.allcarsinone.databinding.ActivityPaymentBinding

class BookTestDriveActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityBookTestDriveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityBookTestDriveBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.bookTestDriveBackbuttonBtn.setOnClickListener {
            finish()
        }
        viewBinding.bookTestDriveBookBtn.setOnClickListener {
            finish()
        }
    }


}