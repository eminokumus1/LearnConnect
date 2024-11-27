package com.eminokumus.learnconnect.lessons

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.eminokumus.learnconnect.databinding.FragmentLessonsBinding
import com.eminokumus.learnconnect.main.MainActivity
import javax.inject.Inject


class LessonsFragment : Fragment() {
    private lateinit var binding: FragmentLessonsBinding

    @Inject
    lateinit var viewModel: LessonsViewModel

    private val args: LessonsFragmentArgs by navArgs()

    private lateinit var lessonsAdapter: LessonsAdapter
    private lateinit var player: ExoPlayer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessonsBinding.inflate(layoutInflater, container, false)

        viewModel.setLessonVideosList(args.course.lessonVideos)


        lessonsAdapter = LessonsAdapter().also {
            it.onLessonItemClickListener = object: OnLessonItemClickListener{
                override fun onItemClick(lessonVideoUrl: String) {
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
    }

    override fun onResume() {
        super.onResume()
        player.playWhenReady = true
        player.play()
    }

    private fun observeViewModel(){
        observeLessonVideosList()
        observeCurrentLessonVideoUrl()
    }

    private fun observeLessonVideosList() {
        viewModel.lessonVideosList.observe(viewLifecycleOwner){videoList->
            if (videoList != null) {
                lessonsAdapter.lessonsList = videoList
            }
        }
    }

    private fun observeCurrentLessonVideoUrl(){
        viewModel.currentLessonVideoUrl.observe(viewLifecycleOwner){
            player.setMediaItem(MediaItem.fromUri(it))
            player.prepare()
            player.playWhenReady = true
        }
    }
}