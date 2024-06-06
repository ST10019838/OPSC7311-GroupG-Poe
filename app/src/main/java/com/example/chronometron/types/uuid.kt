package com.example.chronometron.types

import java.util.UUID

data class CustomUUID(
    val value: UUID = UUID.randomUUID()
)
