package com.eminokumus.learnconnect.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.eminokumus.learnconnect.Constants
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.ThemeModes
import com.eminokumus.learnconnect.databinding.FragmentProfileBinding
import com.eminokumus.learnconnect.login.LoginActivity
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.utils.myApplication
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModel: ProfileViewModel

    private var user: User? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
        user = ((activity as MainActivity).application as MyApplication).currentUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setCurrentUserData(user)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkThemeSwitchIfInLightMode()
        setOnClickListeners()
    }



    override fun onResume() {
        super.onResume()
        checkThemeSwitchIfInLightMode()
    }

    private fun setOnClickListeners() {
        setGoToFavoritesButtonOnClickListener()
        setThemeModeSwitchOnClickListener()
        setSignOutButtonOnClickListener()
    }

    private fun setGoToFavoritesButtonOnClickListener(){
        binding.goToFavoritesButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToCoursesFragment(Constants.FAVORITES_SCREEN))
        }
    }

    private fun setThemeModeSwitchOnClickListener() {
        binding.themeModeSwitch.setOnClickListener {
            if (binding.themeModeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                (activity as MainActivity).myApplication().currentThemeMode = ThemeModes.LIGHT
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                (activity as MainActivity).myApplication().currentThemeMode = ThemeModes.DARK

            }
        }
    }

    private fun setSignOutButtonOnClickListener(){
        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
    }

    private fun checkThemeSwitchIfInLightMode() {
        val currentThemeMode = (activity as MainActivity).myApplication().currentThemeMode
        if (currentThemeMode == ThemeModes.LIGHT) {
            binding.themeModeSwitch.isChecked = true
        }else if (currentThemeMode == ThemeModes.DARK){
            binding.themeModeSwitch.isChecked = false
        }
    }


}