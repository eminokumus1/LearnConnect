package com.eminokumus.learnconnect.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.learnconnect.databinding.ItemLessonBinding
import java.lang.StringBuilder

class LessonsAdapter : RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    var lessonsList = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onLessonItemClickListener: OnLessonItemClickListener? = null

    class LessonsViewHolder(private val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: String,
            itemPosition: Int,
            onLessonItemClickListener: OnLessonItemClickListener?
        ) {
            val lessonName = StringBuilder().append("Lesson ").append("${itemPosition + 1}").toString()
            binding.lessonNameText.text =
                lessonName

            binding.root.setOnClickListener {
                onLessonItemClickListener?.onItemClick(item, lessonName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val binding = ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lessonsList.size
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val item = lessonsList[position]
        holder.bind(item, position, onLessonItemClickListener)
    }
}

interface OnLessonItemClickListener {
    fun onItemClick(lessonVideoUrl: String, lessonName: String)
}