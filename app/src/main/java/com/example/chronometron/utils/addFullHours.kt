package com.example.chronometron.utils

import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours

fun addFullHours(hours1: Hours, hours2: Hours): Hours{
    var minutes = hours1.minutes + hours2.minutes
    var hours = hours1.hours + hours2.hours

    val minutesInHours = 60

    hours += minutes / minutesInHours
    minutes %= minutesInHours

    return FullHours(hours = hours, minutes = minutes)
}