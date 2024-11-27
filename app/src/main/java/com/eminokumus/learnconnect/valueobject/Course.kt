package com.eminokumus.learnconnect.valueobject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val lessonVideos: List<String>? = null
): Parcelable

//@Parcelize
//data class LessonVideos(
//    val lessonVideosList: List<String>,
//): Parcelable


//data class Evaluation(
//    val user: User,
//    val comment: String,
//    val rating: Int
//)