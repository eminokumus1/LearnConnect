package com.eminokumus.learnconnect.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.databinding.ActivityMainBinding
import com.eminokumus.learnconnect.di.AppComponent
import com.eminokumus.learnconnect.valueobject.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {

        appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        println((application as MyApplication).currentUser)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.myNavHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottombar.setupWithNavController(navController)
        bottomNavItemChangeListener(binding.bottombar, navController)
    }


    private fun bottomNavItemChangeListener(
        bottombar: BottomNavigationView,
        navController: NavController
    ) {
        bottombar.setOnItemSelectedListener { item ->
            if (item.itemId != bottombar.selectedItemId) {
                navController.popBackStack(item.itemId, true, saveState = false)
                navController.navigate(item.itemId)
            } else if (item.itemId == bottombar.selectedItemId) {
                navController.navigate(item.itemId)
            }
            true
        }
    }

    private fun observeViewModel() {
        viewModel.currentUser.observe(this){user->
            if(user != null){
                (application as MyApplication).currentUser = user
            }

        }
    }
}