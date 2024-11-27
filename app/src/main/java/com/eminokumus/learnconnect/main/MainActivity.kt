package com.eminokumus.learnconnect.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.databinding.ActivityMainBinding
import com.eminokumus.learnconnect.di.AppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                navController.popBackStack(bottombar.selectedItemId, false
                    , saveState = false)
                navController.navigate(item.itemId)
            } else if (item.itemId == bottombar.selectedItemId) {
                navController.popBackStack(item.itemId, false)
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