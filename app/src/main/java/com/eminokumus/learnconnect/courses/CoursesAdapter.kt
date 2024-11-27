package com.eminokumus.learnconnect.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.learnconnect.databinding.ItemCourseBinding
import com.eminokumus.learnconnect.valueobject.Course

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    var coursesList = listOf<Course>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCourseItemClickListener: OnCourseItemClickListener? = null

    class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(item: Course, onCourseItemClickListener: OnCourseItemClickListener?){
                binding.courseNameText.text = item.title

                binding.root.setOnClickListener {
                    onCourseItemClickListener?.onItemClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val courseItem = coursesList[position]
        holder.bind(courseItem, onCourseItemClickListener)
    }

}

interface OnCourseItemClickListener{
    fun onItemClick(course: Course)
}