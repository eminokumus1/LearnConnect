package com.eminokumus.learnconnect.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.learnconnect.R
import com.eminokumus.learnconnect.databinding.ActivityLoginBinding
import com.eminokumus.learnconnect.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSignupChoiceButtonOnClickListener()
    }

    private fun setSignupChoiceButtonOnClickListener(){
        binding.signupChoiceButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}