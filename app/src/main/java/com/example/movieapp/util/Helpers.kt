package com.example.movieapp.util

import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun TextView.formatMediaDate(inputTime: String?) = if (!inputTime.isNullOrEmpty()) {
    // Making SDF object by giving input time patter
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // inputPatter: yyyy-MM-dd
    // Parsing inputTime
    val parsedDate: Date? = sdf.parse(inputTime)
    // Formatting parsed input time/date
    val formattedTime = SimpleDateFormat("yyyy", Locale.getDefault()).format(parsedDate)
    // Setting time to this textview
    this.text = formattedTime
}else text = "Unknown"

fun TextView.formatUpcomingDate(inputTime: String?) = if (!inputTime.isNullOrEmpty()) {
    // Making SDF object by giving input time pattern
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // Parsing inputTime
    val parsedDate: Date? = sdf.parse(inputTime)
    // Formatting parsed input time/date
    val formattedTime = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(parsedDate)
    // Setting time to this textview
    this.text = "Coming on $formattedTime"
} else
    this.text = "Unknown"

fun TextView.formatUpcomingTv(inputTime: String?) = if (!inputTime.isNullOrEmpty()) {
    // Making SDF object by giving input time pattern
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    // Parsing inputTime
    val parsedDate: Date? = sdf.parse(inputTime)
    // Formatting parsed input time/date
    val formattedTime = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(parsedDate)
    // Setting time to this textview
    this.text = "New episode coming on $formattedTime"
} else
    this.text = "Unknown"
