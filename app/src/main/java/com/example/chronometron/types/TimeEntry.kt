package com.example.chronometron.types

import android.graphics.Bitmap
import com.chargemap.compose.numberpicker.FullHours
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
)
