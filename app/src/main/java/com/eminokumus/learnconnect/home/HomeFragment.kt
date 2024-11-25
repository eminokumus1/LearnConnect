package com.eminokumus.learnconnect.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eminokumus.learnconnect.databinding.FragmentHomeBinding
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.valueobject.Course
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModel: HomeViewModel

    private val homeAdapter = HomeAdapter().also {
        it.onCourseItemClickListener = object : OnCourseItemClickListener{
            override fun onItemClick(course: Course) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCourseDetailFragment(course))
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
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.homeRecycler.adapter = homeAdapter
    }

    private fun observeViewModel() {
        viewModel.coursesListLiveData.observe(viewLifecycleOwner){
            homeAdapter.coursesList = it
        }
    }

}