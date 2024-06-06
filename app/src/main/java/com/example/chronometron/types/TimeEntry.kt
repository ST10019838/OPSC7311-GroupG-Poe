package com.example.chronometron.types

import android.graphics.Bitmap
import java.util.Date
import java.util.UUID

data class TimeEntry(
    var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var date: Date = Date(),
    var startTime: SerializableHours = SerializableHours(),
    var endTime: SerializableHours = SerializableHours(),
    var duration: SerializableHours = SerializableHours(),
    var category: Category = Category(),
    var photograph: Bitmap? = null
) {
    // No-argument constructor
    constructor() : this("", "", Date(), SerializableHours(), SerializableHours(), SerializableHours(), Category(), null)
}
