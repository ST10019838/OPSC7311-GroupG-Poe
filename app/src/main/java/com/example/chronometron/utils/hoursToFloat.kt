package com.example.chronometron.utils

import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import kotlin.math.min

fun hoursToFloat(hours: Hours): Float {
    var minutes: Float = hours.minutes.toFloat()/ 100f
    var hours: Float = hours.hours.toFloat()

    return minutes + hours;
}