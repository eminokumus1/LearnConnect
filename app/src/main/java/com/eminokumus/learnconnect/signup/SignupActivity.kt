package com.eminokumus.learnconnect.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.learnconnect.R
import com.eminokumus.learnconnect.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBackButtonOnClickListener()
    }

    private fun setBackButtonOnClickListener(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}