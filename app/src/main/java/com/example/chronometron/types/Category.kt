package com.example.chronometron.types

import ch.benlu.composeform.fields.PickerValue
import java.util.UUID

data class Category(
    var id: UUID,
    var name: String,
    // var icon
)