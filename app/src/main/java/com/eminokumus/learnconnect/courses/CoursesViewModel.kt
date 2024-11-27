package com.eminokumus.learnconnect.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.learnconnect.Constants
import com.eminokumus.learnconnect.valueobject.Course
import com.eminokumus.learnconnect.valueobject.User
import javax.inject.Inject

class CoursesViewModel @Inject constructor(private val repository: CoursesRepository) :
    ViewModel() {

    var coursesListLiveData: LiveData<MutableList<Course>> =
        repository.fetchAllCoursesFromFirebase()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    private val _itemList = MutableLiveData<MutableList<Course>>()
    val itemList: LiveData<MutableList<Course>> get() = _itemList

    private val _searchList = MutableLiveData<MutableList<Course>>(mutableListOf())
    val searchList: LiveData<MutableList<Course>> get() = _searchList


    fun setCurrentUser(user: User?) {
        _currentUser.value = user
    }


    fun setItemListAccordingToScreenType(screenType: Int) {
        if (screenType == Constants.COURSES_SCREEN) {
            _itemList.value = coursesListLiveData.value
        } else if (screenType == Constants.MY_COURSES_SCREEN) {
            _itemList.value = _currentUser.value?.myCourses
        } else if (screenType == Constants.FAVORITES_SCREEN) {
            _itemList.value = _currentUser.value?.favoriteCourses
        }
    }

    fun searchInItemList(searchKeyword: String) {
        val list = mutableListOf<Course>()
        itemList.value?.let {
            for (item in it) {
                if (item.title!!.contains(searchKeyword, ignoreCase = true)) {
                    list.add(item)
                }
            }
        }
        if (list.isNotEmpty()){
            _searchList.value = list
        }
    }
}
