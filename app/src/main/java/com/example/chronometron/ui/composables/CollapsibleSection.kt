package com.example.chronometron.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
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
fun CollapsibleSection(
    heading: String,
    isOpen: Boolean = true,
    icon: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    var isOpen by rememberSaveable { mutableStateOf(isOpen) }
    val rotation by animateFloatAsState(if (isOpen) 180f else 0f, label = "arrowRotationAnimation")

    Column {
        TextButton(
            onClick = { isOpen = !isOpen },
            modifier = Modifier.fillMaxWidth(),
//            colors = if (isOpen) ButtonColors(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.background,
//                disabledContainerColor = MaterialTheme.colorScheme.primary,
//                disabledContentColor = MaterialTheme.colorScheme.primary
//            ) else ButtonDefaults.textButtonColors()
        ) {
            icon()
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))

            Text("$heading")

            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
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