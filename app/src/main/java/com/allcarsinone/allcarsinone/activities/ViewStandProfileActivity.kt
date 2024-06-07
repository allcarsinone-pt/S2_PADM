package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allcarsinone.allcarsinone.R
import com.allcarsinone.allcarsinone.databinding.ActivityViewStandProfileBinding

class ViewStandProfileActivity : AppCompatActivity() {

    private val viewBinding: ActivityViewStandProfileBinding by lazy { ActivityViewStandProfileBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

    }

    fun getStandDetails(standid: Int) {
        //val
    }
}