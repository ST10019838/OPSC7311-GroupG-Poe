package com.example.chronometron.utils

import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.FirebaseHours

fun addFullHours(hours1: FirebaseHours, hours2: FirebaseHours): FirebaseHours {
    var minutes = hours1.minutes!! + hours2.minutes!!
    var hours = hours1.hours!! + hours2.hours!!

    val minutesInHours = 60

    hours += minutes / minutesInHours
    minutes %= minutesInHours

    return FirebaseHours(hours = hours, minutes = minutes)
}