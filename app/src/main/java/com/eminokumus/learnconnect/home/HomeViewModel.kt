package com.eminokumus.learnconnect.home
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.valueobject.Course
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    var coursesListLiveData: LiveData<MutableList<Course>> = repository.fetchAllCoursesFromFirebase()

}
