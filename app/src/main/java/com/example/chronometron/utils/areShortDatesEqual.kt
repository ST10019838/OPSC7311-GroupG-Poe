package com.example.chronometron.utils

import ch.benlu.composeform.formatters.dateShort
import java.util.Date

fun areShortDatesEqual(shortDate: String, date: Date): Boolean {
    return shortDate == dateShort(date)
}