package com.example.chronometron.api

import com.example.chronometron.db.Database.isDarkMode


fun toggleDarkMode(value: Boolean) {
    isDarkMode.setValue(value)
}