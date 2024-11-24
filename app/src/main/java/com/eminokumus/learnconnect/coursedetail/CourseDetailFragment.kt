package com.eminokumus.learnconnect.coursedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eminokumus.learnconnect.R


class CourseDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.progressbar.start()
//        viewModel.fetchUserData(onSuccess = {
//            checkIfInFavoriteCourses("")
//            checkIfInMyCourses("")
//            binding.progressbar.cancel()
//        }, onError = {
//            dialog("Bir hatayla karşılaşıldı", onOkayButtonClicked = {
//                binding.root.findNavController.navigate()
//            })
//        })
    }
//
//    private fun checkIfInMyCourses(courseId: String){
//        val course = userData.myCourses.find{
//            it.id == courseId
//        }
//
//        course?.let{
//            binding.button.text = "Drop course"
//        }
//
//    }
//
//
//    private fun checkIfInFavoriteCourses(courseId: String){
//        val course = userData.favoriteCourses.find{
//            it.id == courseId
//        }
//
//        course?.let{
//            binding.favoriteFab.icon = R.drawable.ic_favorite_filled
//        }
//
//    }
}