package com.eminokumus.learnconnect.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.ThemeModes
import com.eminokumus.learnconnect.databinding.ActivityLoginBinding
import com.eminokumus.learnconnect.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkThemeSwitchIfInLightMode()

        viewModel = LoginViewModel()
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
            if (binding.emailField.isFocused){
                showErrorIfEmailNotValid()
            }
            if (isValid && binding.emailField.isFocused) {
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
//            showErrorIfPasswordNotValid()
        }
    }

    private fun showErrorIfEmailNotValid(){
        if (viewModel.isEmailValid.value == true){
            binding.emailField.error = null
        } else {
            binding.emailField.error = "Wrong email format"
        }

    }
    private fun showErrorIfPasswordNotValid(){
        if (viewModel.isPasswordValid.value == true){
            binding.passwordField.error = null
        } else {
            binding.passwordField.error = "Wrong email format"
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
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // TODO: Check if the user is in firebase
            } else {
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT)
                    .show()
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


}