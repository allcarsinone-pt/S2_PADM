package com.allcarsinone.allcarsinone.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityPaymentBinding
import com.allcarsinone.allcarsinone.databinding.ActivityPaymentDoneBinding

class PaymentDone : AppCompatActivity() {
    private lateinit var viewBinding: ActivityPaymentDoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityPaymentDoneBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.PaymentDoneHomeBTN.setOnClickListener {
            finish()
            val intent = Intent(this, InitialPageActivity::class.java)
            startActivity(intent)
        }
    }
}