package com.example.chronometron.api

import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.Firebase.Database.maximumGoal
import com.example.chronometron.Firebase.Database.minimumGoal


fun updateMinimumGoal(goal: Hours) {
    minimumGoal.setValue(goal)
}

fun updateMaximumGoal(goal: Hours) {
    maximumGoal.setValue(goal)
}




