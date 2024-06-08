package com.example.chronometron.utils

import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.FirebaseHours
import kotlin.math.min

fun hoursToFloat(hours: FirebaseHours): Float {
    var minutes: Float = hours.minutes!!.toFloat()/ 100f
    var hours: Float = hours.hours!!.toFloat()

    return minutes + hours;
}