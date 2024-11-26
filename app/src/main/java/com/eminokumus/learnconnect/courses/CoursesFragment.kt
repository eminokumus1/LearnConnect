package com.eminokumus.learnconnect.courses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.learnconnect.databinding.FragmentCoursesBinding
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.utils.myApplication
import com.eminokumus.learnconnect.valueobject.Course
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject

class CoursesFragment : Fragment() {
    private lateinit var binding: FragmentCoursesBinding

    @Inject
    lateinit var viewModel: CoursesViewModel

    private val args: CoursesFragmentArgs by navArgs()

    private val screenType by lazy {
        args.screenType
    }


    private var user: User? = null


    private val coursesAdapter = HomeAdapter().also {
        it.onCourseItemClickListener = object : OnCourseItemClickListener {
            override fun onItemClick(course: Course) {
                findNavController().navigate(
                    CoursesFragmentDirections.actionHomeFragmentToCourseDetailFragment(
                        course
                    )
                )
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesBinding.inflate(layoutInflater, container, false)

        user = (activity as MainActivity).myApplication().currentUser
        viewModel.setCurrentUser(user)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.coursesRecycler.adapter = coursesAdapter
    }

    private fun observeViewModel() {
        observeUser()
        observeCoursesListLiveData()
        observeItemList()
    }

    private fun observeUser() {
        viewModel.currentUser.observe(viewLifecycleOwner) {
            viewModel.setItemListAccordingToScreenType(screenType)
        }
    }

    private fun observeCoursesListLiveData() {
        viewModel.coursesListLiveData.observe(viewLifecycleOwner) {
            viewModel.setItemListAccordingToScreenType(screenType)
        }
    }

    private fun observeItemList() {
        viewModel.itemList.observe(viewLifecycleOwner) { itemList ->
            if (itemList != null){
                coursesAdapter.coursesList = itemList

            }
        }
    }


}