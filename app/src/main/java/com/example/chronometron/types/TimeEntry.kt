package com.example.chronometron.types

import android.graphics.Bitmap
import java.util.Date
import java.util.UUID

data class TimeEntry(
    var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var date: Date = Date(),
    var startTime: SerializableHours = SerializableHours(),
    var endTime: SerializableHours = SerializableHours(),
    var duration: SerializableHours = SerializableHours(),
    var category: Category = Category(),
    var photograph: Bitmap? = null,
    var isArchived: Boolean
) {
    // No-argument constructor
    constructor() : this("", "", Date(), SerializableHours(), SerializableHours(), SerializableHours(), Category(), null, false)
}

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
