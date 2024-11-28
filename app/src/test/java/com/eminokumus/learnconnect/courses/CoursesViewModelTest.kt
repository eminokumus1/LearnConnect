package com.eminokumus.learnconnect.courses

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.eminokumus.learnconnect.Constants
import com.eminokumus.learnconnect.valueobject.Course
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


class CoursesViewModelTest {

    // For MainLooper mocking
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val coursesRepository: CoursesRepository = mock()
    private lateinit var viewModel: CoursesViewModel

    @Before
    fun setup() {
        whenever(coursesRepository.fetchAllCoursesFromFirebase()).thenReturn(
            MutableLiveData<MutableList<Course>>(
                mutableListOf(
                    Course(title = "Mobile", category = "Android"),
                    Course(title = "Backend", category = "Java"),
                    Course(title = "Frontend", category = "React JS")
                )
            )
        )
        viewModel = CoursesViewModel(coursesRepository)
    }

    @Test
    fun `test searchInItemList clears searchList value when itemList value is null`() {
        // Given
        val oldList = viewModel.searchList.value

        // When
        viewModel.searchInItemList("sdsdsd")
        val newList = viewModel.searchList.value

        // Then
        Assert.assertTrue(oldList !== newList)
        Assert.assertTrue(newList?.isEmpty() == true)
        Assert.assertNull(viewModel.itemList.value)
    }

    @Test
    fun `test searchInItemList filters itemList and sets searchList`() {
        //Given
        val searchKeyword = "Mobile"

        //When
        viewModel.setItemListAccordingToScreenType(Constants.COURSES_SCREEN)
        viewModel.searchInItemList(searchKeyword)

        //Then
        Assert.assertEquals(1, viewModel.searchList.value?.size)
        Assert.assertEquals("Mobile", viewModel.searchList.value?.first()?.title)
    }

    @Test
    fun `test filterItemListBasedOnCategory clears searchList value when itemList value is null`(){
        // Given
        val oldList = viewModel.searchList.value

        // When
        viewModel.filterItemListBasedOnCategory("sdsdsd")
        val newList = viewModel.searchList.value

        // Then
        Assert.assertTrue(oldList !== newList)
        Assert.assertTrue(newList?.isEmpty() == true)
        Assert.assertNull(viewModel.itemList.value)
    }

    @Test
    fun `test filterItemListBasedOnCategory filters itemList and sets searchList`() {
        //Given
        val category = "React JS"

        //When
        viewModel.setItemListAccordingToScreenType(Constants.COURSES_SCREEN)
        viewModel.filterItemListBasedOnCategory(category)

        //Then
        Assert.assertEquals(1, viewModel.searchList.value?.size)
        Assert.assertEquals("React JS", viewModel.searchList.value?.first()?.category)
    }


}