package com.example.chronometron.types

import com.chargemap.compose.numberpicker.Hours

data class Goals(
    var minimumHours: SerializableHours = SerializableHours(),
    var maximumHours: SerializableHours = SerializableHours()
)
