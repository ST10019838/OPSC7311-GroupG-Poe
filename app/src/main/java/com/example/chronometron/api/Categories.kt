package com.example.chronometron.api

import com.example.chronometron.Firebase.Database.categories
import com.example.chronometron.types.Category


fun addCategory(newCategory: Category) {
    val newId = categories.push().key
    newCategory.id = newId!!
    categories.child(newId).setValue(newCategory)
}

fun removeCategory(category: Category) {
    categories.child(category.id!!).setValue(null)
}




