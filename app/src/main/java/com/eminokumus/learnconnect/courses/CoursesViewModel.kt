package com.eminokumus.learnconnect.courses
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.Constants
import com.eminokumus.learnconnect.valueobject.Course
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject

class CoursesViewModel @Inject constructor(private val repository: CoursesRepository) : ViewModel() {

    var coursesListLiveData: LiveData<MutableList<Course>> = repository.fetchAllCoursesFromFirebase()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    private val _itemList = MutableLiveData<MutableList<Course>>()
    val itemList: LiveData<MutableList<Course>> get() = _itemList
//
//    private val _myCoursesList = MutableLiveData<MutableList<Course>>()
//    val myCoursesList: LiveData<MutableList<Course>> get() = _myCoursesList
//
//    private val _favoriteCoursesList = MutableLiveData<MutableList<Course>>()
//    val favoriteCoursesList: LiveData<MutableList<Course>> get() = _favoriteCoursesList

    fun setCurrentUser(user: User?){
        _currentUser.value = user
    }

//    fun setMyCoursesList(myCourses: MutableList<Course>){
//        _myCoursesList.value = myCourses
//    }
//
//    fun setFavoriteCoursesList(favoriteCourses: MutableList<Course>){
//        _favoriteCoursesList.value = favoriteCourses
//    }

    fun setItemListAccordingToScreenType(screenType: Int){
        if (screenType == Constants.COURSES_SCREEN){
            _itemList.value = coursesListLiveData.value
        }else if(screenType == Constants.MY_COURSES_SCREEN){
            _itemList.value = _currentUser.value?.myCourses
        }else if(screenType == Constants.FAVORITES_SCREEN){
            _itemList.value = _currentUser.value?.favoriteCourses
        }
    }

}
