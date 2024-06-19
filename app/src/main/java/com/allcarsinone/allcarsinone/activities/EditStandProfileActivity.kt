package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.Globals
import com.allcarsinone.allcarsinone.databinding.ActivityEditStandProfileBinding

class EditStandProfileActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityEditStandProfileBinding
    private val usersAPI by lazy { Globals.userAPI }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditStandProfileBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.EditStandProfileEditBtn.setOnClickListener {
            finish()
        }
        viewBinding.EditStandProfileBackbuttonBtn.setOnClickListener {
            finish()
        }
    }

}