package com.eminokumus.learnconnect.coursedetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.eminokumus.learnconnect.MyApplication
import com.eminokumus.learnconnect.R
import com.eminokumus.learnconnect.databinding.FragmentCourseDetailBinding
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject


class CourseDetailFragment : Fragment() {
    private lateinit var binding: FragmentCourseDetailBinding

    @Inject
    lateinit var viewModel: CourseDetailViewModel

    private val args: CourseDetailFragmentArgs by navArgs()

    private var user: User? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailBinding.inflate(layoutInflater, container, false)

        user = ((activity as MainActivity).application as MyApplication).currentUser

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setCurrentUserData(user)
        viewModel.setCourse(args.course)
        viewModel.checkIfInMyCourses()
        viewModel.checkIfInMyFavorites()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        setImageWithGlide()
        setOnClickListeners()

    }

    private fun observeViewModel(){
        observeIsInMyCourses()
        observeIsInFavorites()
    }

    private fun observeIsInMyCourses(){
        viewModel.isInMyCourses.observe(viewLifecycleOwner){isInMyCourses->
            setButtonVisibilities(isInMyCourses)
        }
    }

    private fun observeIsInFavorites(){
        viewModel.isInFavorites.observe(viewLifecycleOwner){isInFavorites->
            setAddFavoritesFabIcon(isInFavorites)
        }
    }

    private fun setButtonVisibilities(isInMyCourses: Boolean){
        if (isInMyCourses){
            showDropCourseButton()
        }else{
            showJoinCourseButton()
        }
    }

    private fun showJoinCourseButton() {
        binding.dropCourseButton.visibility = View.GONE
        binding.joinCourseButton.visibility = View.VISIBLE
    }

    private fun showDropCourseButton() {
        binding.dropCourseButton.visibility = View.VISIBLE
        binding.joinCourseButton.visibility = View.GONE
    }

    private fun setAddFavoritesFabIcon(isInFavorites: Boolean){
        if (isInFavorites){
            binding.addFavoritesFab.setImageResource(R.drawable.ic_favorite_filled)
        }else{
            binding.addFavoritesFab.setImageResource(R.drawable.ic_favorite)
        }
    }

    private fun setImageWithGlide(){
        Glide.with(binding.root)
            .load(viewModel.course.value?.imageUrl)
            .into(binding.courseImage)
    }

    private fun setOnClickListeners(){
        setJoinButtonOnClickListener()
        setDropButtonOnClickListener()
        setAddFavoritesFabOnClickListener()
    }

    private fun setJoinButtonOnClickListener() {
        binding.joinCourseButton.setOnClickListener {
            viewModel.addCourseToMyCourses()
            viewModel.setIsInMyCourses(true)
        }
    }

    private fun setDropButtonOnClickListener() {
        binding.dropCourseButton.setOnClickListener {
            viewModel.removeCourseFromMyCourses()
            viewModel.setIsInMyCourses(false)
        }
    }

    private fun setAddFavoritesFabOnClickListener(){
        binding.addFavoritesFab.setOnClickListener {
            if(viewModel.isInFavorites.value == true){
                viewModel.setIsInFavorites(false)
                viewModel.removeCourseToFavorites()
            }else{
                viewModel.setIsInFavorites(true)
                viewModel.addCourseToFavorites()
            }
        }

    }




}