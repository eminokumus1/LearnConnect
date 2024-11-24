package com.eminokumus.learnconnect.mycourses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.learnconnect.databinding.ItemMyCoursesBinding
import com.eminokumus.learnconnect.valueobject.Course

class MyCoursesAdapter : RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder>() {
    var myCoursesList = listOf<Course>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onMyCourseItemClickListener: OnMyCourseItemClickListener? = null

    class MyCoursesViewHolder(private val binding: ItemMyCoursesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Course, onMyCourseItemClickListener: OnMyCourseItemClickListener?) {
            binding.courseNameText.text = item.title

            binding.root.setOnClickListener {
                onMyCourseItemClickListener?.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCoursesViewHolder {
        val binding =
            ItemMyCoursesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCoursesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myCoursesList.size
    }

    override fun onBindViewHolder(holder: MyCoursesViewHolder, position: Int) {
        val myCourseItem = myCoursesList[position]
        holder.bind(myCourseItem, onMyCourseItemClickListener)
    }

}

interface OnMyCourseItemClickListener {
    fun onItemClick(myCourse: Course)
}