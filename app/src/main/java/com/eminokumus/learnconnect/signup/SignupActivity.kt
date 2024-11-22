package com.eminokumus.learnconnect.signup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.ThemeModes
import com.eminokumus.learnconnect.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkThemeSwitchIfInLightMode()


        viewModel = SignupViewModel()
        observeViewModel()
        checkFields()

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setBackButtonOnClickListener()
        setRootOnClickListener()
        setSignupButtonOnClickListener()
        setThemeModeSwitchOnClickListener()
    }

    private fun setSignupButtonOnClickListener() {
        binding.signupButton.setOnClickListener {
            if (isSignupAllowed()) {
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                // TODO: Save user to firebase
            } else {
                Toast.makeText(this, "Check the informations you entered", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun setBackButtonOnClickListener() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setRootOnClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
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

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun observeViewModel() {
        observeEmail()
        observePassword()
        observeConfirmationPassword()
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

    private fun observePassword() {
        viewModel.isPasswordValid.observe(this) { isValid ->
            if (isValid) {
                binding.passwordField.error = null
            } else {
                binding.passwordField.error = "Minimum 6 characters"
            }
        }
    }

    private fun observeConfirmationPassword() {
        viewModel.isPasswordConfirm.observe(this) { isConfirmed ->
            if (isConfirmed) {
                binding.confirmPasswordField.error = null
            } else {
                binding.confirmPasswordField.error = "Doesn't match with password"
            }
        }
    }


    private fun isSignupAllowed(): Boolean {
        return viewModel.isEmailValid.value == true &&
                viewModel.isPasswordValid.value == true &&
                viewModel.isPasswordConfirm.value == true
    }

    private fun checkFields() {
        binding.emailEditText.doAfterTextChanged {
            viewModel.checkEmailFormat(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.doAfterTextChanged {
            viewModel.checkPasswordFormat(binding.passwordEditText.text.toString())
        }
        binding.confirmPasswordEditText.doAfterTextChanged {
            viewModel.checkConfirmPassword(
                binding.confirmPasswordEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }


    override fun onResume() {
        super.onResume()
        binding.emailField.error = null
        binding.passwordField.error = null
        binding.confirmPasswordField.error = null
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