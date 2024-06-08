package com.example.chronometron.types

import android.graphics.Bitmap
import com.chargemap.compose.numberpicker.FullHours
//import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.chargemap.compose.numberpicker.Hours
import java.util.Date
import java.util.UUID

//@Entity()
data class TimeEntry(
//    @PrimaryKey(autoGenerate = true)
    var id: String = "",
    var description: String = "",
    var date: Date = Date(),
    var startTime: FirebaseHours? = null,
    var endTime: FirebaseHours? = null,
    var duration: FirebaseHours? = null,
    var category: Category = Category(),
    var photograph: String? = null,
    var isArchived: Boolean = false
)

//{
//
//    // The following search functionality was adapted from youtube
//    // Author: Philipp Lackner
//    // Link: https://www.youtube.com/watch?v=CfL6Dl2_dAE
//    fun isBetweenSelectablePeriod(fromDate: Date?, toDate: Date?): Boolean {
//        return if (fromDate == null && toDate != null) {
//            date <= toDate
//        } else if (fromDate != null && toDate == null) {
//            fromDate <= date
//        } else fromDate!! <= date && date <= toDate!!
//    }
//}

