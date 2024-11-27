package com.eminokumus.learnconnect.lessons

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.eminokumus.learnconnect.databinding.FragmentLessonsBinding
import com.eminokumus.learnconnect.main.MainActivity
import com.eminokumus.learnconnect.utils.myApplication
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class LessonsFragment : Fragment() {
    private lateinit var binding: FragmentLessonsBinding

    @Inject
    lateinit var viewModel: LessonsViewModel

    private val args: LessonsFragmentArgs by navArgs()

    private val course by lazy {
        args.course
    }

    private lateinit var lessonsAdapter: LessonsAdapter
    private lateinit var player: ExoPlayer

    private var userId: String? = null

    private var currentLessonPosition: Long = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessonsBinding.inflate(layoutInflater, container, false)

        userId = (activity as MainActivity).myApplication().currentUser?.userId

        viewModel.setLessonVideosList(course.lessonVideos)

        getLessonPosition()

        lessonsAdapter = LessonsAdapter().also {
            it.onLessonItemClickListener = object : OnLessonItemClickListener {
                override fun onItemClick(lessonVideoUrl: String, lessonName: String) {
                    saveLessonPosition(player.currentPosition)
                    changeLessonText(lessonName)
                    viewModel.setCurrentLessonIndexWith(lessonVideoUrl)
                    viewModel.setCurrentLessonVideoUrl(lessonVideoUrl)
                }

            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.lessonsRecycler.adapter = lessonsAdapter

        player = ExoPlayer.Builder(requireContext()).build()
        binding.lessonPlayer.player = player


    }

    override fun onStop() {
        super.onStop()
        player.playWhenReady = false
        player.stop()
        saveLessonPosition(player.currentPosition)
    }

    override fun onResume() {
        super.onResume()
        player.playWhenReady = true
        player.play()
    }

    private fun observeViewModel() {
        observeLessonVideosList()
        observeCurrentLessonVideoUrl()
//        observeCurrentLessonPosition()
    }

    private fun observeLessonVideosList() {
        viewModel.lessonVideosList.observe(viewLifecycleOwner) { videoList ->
            if (videoList != null) {
                lessonsAdapter.lessonsList = videoList
            }
        }
    }

    private fun observeCurrentLessonVideoUrl() {
        viewModel.currentLessonVideoUrl.observe(viewLifecycleOwner) {newLessonVideoUrl->
            getLessonPosition()
            player.setMediaItem(MediaItem.fromUri(newLessonVideoUrl))
            player.prepare()
            println("current position: ${currentLessonPosition}")
            player.seekTo(currentLessonPosition)
            player.playWhenReady = true
        }
    }

//    private fun observeCurrentLessonPosition(){
//        viewModel.currentLessonPosition.observe(viewLifecycleOwner){
//            if (it != null){
//                currentLessonPosition = it
//                println("observe current lesson position: $currentLessonPosition")
//
//            }
//        }
//    }


    private fun saveLessonPosition(currentPosition: Long) {
        val lessonPositionKey = StringBuilder()
            .append(userId)
            .append(course.id)
            .append(viewModel.getCurrentLessonIndex())
            .toString()
        lifecycleScope.launch {
            viewModel.saveLessonVideoPosition(lessonPositionKey, currentPosition)
            println(" current position saved $lessonPositionKey, $currentPosition")
        }
    }




    private fun getLessonPosition(){
        val lessonPositionKey = StringBuilder()
            .append(userId)
            .append(course.id)
            .append(viewModel.getCurrentLessonIndex())
            .toString()
        lifecycleScope.launch {
            viewModel.getLessonVideoPosition(lessonPositionKey).collect{
                currentLessonPosition = it ?: 0
                println(" current position inside scope: $lessonPositionKey, $currentLessonPosition")
            }
        }
    }

    private fun changeLessonText(lessonName: String){
        binding.lessonText.text = lessonName
    }
}