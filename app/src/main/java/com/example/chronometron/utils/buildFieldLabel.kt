package com.example.chronometron.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun buildFieldLabel(label: String, isRequired: Boolean ): AnnotatedString{
    return buildAnnotatedString {
        append("$label")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        ) {
            if (isRequired) append("*")
        }
    }
}