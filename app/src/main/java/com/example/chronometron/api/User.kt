package com.example.chronometron.api

import com.example.chronometron.Firebase.Database.db
import com.example.chronometron.Firebase.Database.isDarkMode


fun toggleDarkMode(value: Boolean) {
    isDarkMode.setValue(value)
}

fun removeUser() {
    db.setValue(null)
}