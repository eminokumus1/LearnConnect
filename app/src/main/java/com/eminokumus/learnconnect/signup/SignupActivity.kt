package com.eminokumus.learnconnect.signup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.ThemeModes
import com.eminokumus.learnconnect.databinding.ActivitySignupBinding
import com.eminokumus.learnconnect.valueobject.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignupViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var dbFirebase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = SignupViewModel()

        checkThemeSwitchIfInLightMode()

        observeViewModel()
        checkFields()

        auth = Firebase.auth

        dbFirebase = Firebase.database.getReference("users")

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
                createUserForFirebaseAuth()
            } else {
                Toast.makeText(this, "Check the informations you entered", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun createUserForFirebaseAuth(){
        auth.createUserWithEmailAndPassword(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                auth.currentUser?.let { it1 -> addUserToFirebaseDatabase(it1.uid, binding.emailEditText.text.toString()) }
                auth.signOut()
                Toast.makeText(this, "Signup completed", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("error", it.exception.toString())
                Toast.makeText(this, "This email is already in use", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addUserToFirebaseDatabase(userId: String, userEmail: String) {
        dbFirebase.child(userId).child("userData").setValue(User(userId, userEmail))
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