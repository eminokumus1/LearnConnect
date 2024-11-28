package com.eminokumus.learnconnect.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.ThemeModes
import com.eminokumus.learnconnect.databinding.ActivityLoginBinding
import com.eminokumus.learnconnect.signup.SignupActivity
import com.eminokumus.learnconnect.utils.myApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        this.myApplication().appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkThemeSwitchIfInLightMode()

        auth = Firebase.auth
        checkLoggedInUser()

        checkFields()
        observeViewModel()



        setOnClickListeners()
    }



    private fun observeViewModel() {
        observeEmail()
        observePassword()
    }

    private fun observePassword() {
        viewModel.isPasswordValid.observe(this) { isValid ->
            if (isValid) {
                binding.passwordField.error = null
            } else {
                binding.passwordField.error = "Minimum 6 characters"
            }
        }
    }

    private fun observeEmail() {
        viewModel.isEmailValid.observe(this) { isValid ->
            if (isValid) {
                binding.emailField.error = null
            } else {
                binding.emailField.error = "Wrong email format"
            }
        }
    }

    private fun checkFields() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.doAfterTextChanged {
            viewModel.checkPasswordFormat(binding.passwordEditText.text.toString())
        }
    }

    private fun setOnClickListeners(){
        setSignupChoiceButtonOnClickListener()
        setRootOnClickListener()
        setLoginButtonOnClickListener()
        setForgetPasswordButtonOnClickListener()
        setThemeModeSwitchOnClickListener()
    }
    private fun setSignupChoiceButtonOnClickListener(){
        binding.signupChoiceButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRootOnClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun setLoginButtonOnClickListener(){
        binding.loginButton.setOnClickListener {
            if (isLoginAllowed()){
                checkFirebaseAuthentication()
            } else {
                Toast.makeText(this, "Wrong email or password format", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun checkFirebaseAuthentication() {
        showProgressBar()
        auth.signInWithEmailAndPassword(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful){

                    hideProgressBar()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()


            }else{
                hideProgressBar()
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show()
                Log.e("error", it.exception.toString())
            }
        }
    }

    private fun isLoginAllowed(): Boolean {
        return viewModel.isEmailValid.value == true &&
                viewModel.isPasswordValid.value == true
    }

    private fun setForgetPasswordButtonOnClickListener(){
        // TODO: Go to forget password screen with intent
    }

    private fun setThemeModeSwitchOnClickListener() {
        binding.themeModeSwitch.setOnClickListener {
            if (binding.themeModeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                (application as MyApplication).currentThemeMode = ThemeModes.LIGHT
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                (application as MyApplication).currentThemeMode = ThemeModes.DARK

            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.emailField.error = null
        binding.passwordField.error = null
        checkThemeSwitchIfInLightMode()

    }

    private fun checkThemeSwitchIfInLightMode() {
        val currentThemeMode = (application as MyApplication).currentThemeMode
        if (currentThemeMode == ThemeModes.LIGHT) {
            binding.themeModeSwitch.isChecked = true
        }else if (currentThemeMode == ThemeModes.DARK){
            binding.themeModeSwitch.isChecked = false
        }
    }

    private fun showProgressBar(){
        binding.loginProgressBar.visibility = View.VISIBLE
        binding.dimView.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.loginProgressBar.visibility = View.GONE
        binding.dimView.visibility = View.GONE
    }

    private fun checkLoggedInUser(){
        if (auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}