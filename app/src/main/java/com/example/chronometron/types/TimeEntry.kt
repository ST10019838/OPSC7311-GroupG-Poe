package com.example.chronometron.types

import android.graphics.Bitmap
import com.chargemap.compose.numberpicker.Hours
import java.util.Date
import java.util.UUID

data class TimeEntry(
    var id: UUID,
    var description: String,
    var date: Date,
    var startTime: Hours,
    var endTime: Hours,
    var duration: Hours,
    var category: Category,
    var photograph: Bitmap?
) {

    // The following search functionality was adapted from youtube
    // Author: Philipp Lackner
    // Link: https://www.youtube.com/watch?v=CfL6Dl2_dAE
    fun isBetweenSelectablePeriod(fromDate: Date?, toDate: Date?): Boolean {
        return if (fromDate == null && toDate != null) {
            date <= toDate
        } else if (fromDate != null && toDate == null) {
            fromDate <= date
        } else fromDate!! <= date && date <= toDate!!
    }
}

