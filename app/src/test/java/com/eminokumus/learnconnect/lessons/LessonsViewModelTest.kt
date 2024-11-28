package com.eminokumus.learnconnect.lessons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class LessonsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LessonsViewModel
    private lateinit var repository: LessonsRepository

    @Before
    fun setup() {
        repository = mock()
        viewModel = LessonsViewModel(repository)
    }

    @Test
    fun `setLessonVideosList updates lessonVideosList and currentLessonVideoUrl`() {
        val videoList = listOf("url1", "url2", "url3")

        viewModel.setLessonVideosList(videoList)

        Assert.assertEquals(videoList, viewModel.lessonVideosList.value)
        Assert.assertEquals("url1", viewModel.currentLessonVideoUrl.value)
    }

    @Test
    fun `setCurrentLessonVideoUrl updates currentLessonVideoUrl`() {
        val videoUrl = "url1"

        viewModel.setCurrentLessonVideoUrl(videoUrl)

        Assert.assertEquals(videoUrl, viewModel.currentLessonVideoUrl.value)
    }

    @Test
    fun `saveLessonVideoPosition calls repository with correct parameters`(): Unit = runBlocking {
        val lessonPositionKey = "lesson1"
        val currentPosition = 1500L

        viewModel.saveLessonVideoPosition(lessonPositionKey, currentPosition)

        verify(repository).saveLessonPosition(lessonPositionKey, currentPosition)
    }

    @Test
    fun `getLessonVideoPosition calls repository and returns position`(): Unit = runBlocking {
        val lessonPositionKey = "lesson1"
        val expectedPosition = 1500L

        whenever(repository.getLessonPosition(lessonPositionKey)).thenReturn(flowOf(expectedPosition))

        val positionFlow = viewModel.getLessonVideoPosition(lessonPositionKey)

        positionFlow.collect { position ->
            Assert.assertEquals(expectedPosition, position)
        }
    }

}