package com.example.chronometron.types

import ch.benlu.composeform.fields.PickerValue

data class Category(
    var id: Int,
    var name: String,
    // var icon
): PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.name.contains(query)
    }
}