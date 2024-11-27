package com.eminokumus.learnconnect.courses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.learnconnect.R
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


    private val coursesAdapter = CourseAdapter().also {
        it.onCourseItemClickListener = object : OnCourseItemClickListener {
            override fun onItemClick(course: Course) {
                findNavController().navigate(
                    CoursesFragmentDirections.actionCoursesFragmentToCourseDetailFragment(course)
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
        watchSearchEditText()
        setFilterSpinner()
        setFilterButtonOnClickListener()

        binding.coursesRecycler.adapter = coursesAdapter
    }

    private fun observeViewModel() {
        observeUser()
        observeCoursesListLiveData()
        observeItemList()
        observeSearchList()
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
            if (itemList != null) {
                coursesAdapter.coursesList = itemList

            }
        }
    }

    private fun observeSearchList() {
        viewModel.searchList.observe(viewLifecycleOwner) { searchList ->
            if (searchList != null) {
                coursesAdapter.coursesList = searchList
            }
        }
    }

    private fun watchSearchEditText() {
        binding.searchEditText.doAfterTextChanged {
            viewModel.searchInItemList(binding.searchEditText.text.toString())
        }
    }

    private fun setFilterSpinner() {
        val adapter = createArrayAdapterForFilterSpinner()
        binding.filterSpinner.adapter = adapter
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                if (selectedCategory != "Clear All Filter"){
                    viewModel.filterItemListBasedOnCategory(selectedCategory)
                }else{
                    viewModel.filterItemListBasedOnCategory("")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


    }

    private fun createArrayAdapterForFilterSpinner(): ArrayAdapter<CharSequence> {
        return ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_options,
            R.layout.item_filter_spinner
        )
    }

    private fun setFilterButtonOnClickListener() {
        binding.filterImageButton.setOnClickListener {
            binding.filterSpinner.performClick()

        }
    }


}