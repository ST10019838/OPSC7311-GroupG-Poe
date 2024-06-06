package com.example.chronometron.types

import java.util.UUID

data class Category(
    var id: String = UUID.randomUUID().toString(),
    var name: String? = null
) {
    constructor() : this(UUID.randomUUID().toString(), null)
}
