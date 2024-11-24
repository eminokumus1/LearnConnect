package com.eminokumus.learnconnect.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eminokumus.learnconnect.databinding.ItemHomeBinding
import com.eminokumus.learnconnect.valueobject.Course

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var coursesList = listOf<Course>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCourseItemClickListener: OnCourseItemClickListener? = null

    class HomeViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(item: Course, onCourseItemClickListener: OnCourseItemClickListener?){
                binding.courseNameText.text = item.title

                binding.root.setOnClickListener {
                    onCourseItemClickListener?.onItemClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val courseItem = coursesList[position]
        holder.bind(courseItem, onCourseItemClickListener)
    }

}

interface OnCourseItemClickListener{
    fun onItemClick(course: Course)
}