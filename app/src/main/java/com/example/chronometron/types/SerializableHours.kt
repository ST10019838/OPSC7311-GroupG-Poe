package com.example.chronometron.types

import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours

data class SerializableHours(
    var hours: Int = 0,
    var minutes: Int = 0
) {
    // Convert SerializableHours to FullHours
    fun toFullHours(): Hours {
        return FullHours(hours, minutes)
    }

    companion object {
        // Convert FullHours to SerializableHours
        fun fromFullHours(fullHours: Hours): SerializableHours {
            return SerializableHours(fullHours.hours, fullHours.minutes)
        }
    }
}
