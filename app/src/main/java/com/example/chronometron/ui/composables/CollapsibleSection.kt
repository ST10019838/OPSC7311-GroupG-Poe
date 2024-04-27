package com.example.chronometron.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun CollapsibleSection(heading: String, content: @Composable () -> Unit) {
    var isOpen by rememberSaveable { mutableStateOf(true) }
    val rotation by animateFloatAsState(if (isOpen) 180f else 0f, label = "arrowRotationAnimation")

    Column {
        TextButton(
            onClick = { isOpen = !isOpen },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("$heading ")

            Icon(
                Icons.Filled.KeyboardArrowDown,
                contentDescription = "arrow",
                modifier = Modifier.rotate(rotation)
            )
        }

        AnimatedVisibility(visible = isOpen) {
            content()
        }

    }
}