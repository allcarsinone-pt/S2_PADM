package com.allcarsinone.allcarsinone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.databinding.ActivityEditProfilePhotoBinding

class EditProfilePhotoActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityEditProfilePhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditProfilePhotoBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

    }
}