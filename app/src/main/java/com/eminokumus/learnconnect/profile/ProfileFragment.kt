package com.eminokumus.learnconnect.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.R
import com.eminokumus.learnconnect.databinding.FragmentProfileBinding
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.valueobject.User
import com.google.firebase.auth.FirebaseUser
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

//        viewModel.currentUserData.observe(viewLifecycleOwner){user->
//            binding.emailText.text = user?.email
//            binding.numberOfTotalCoursesText.text = user?.myCourses?.size.toString()
//        }

    }




}