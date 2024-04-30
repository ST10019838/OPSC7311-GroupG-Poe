package com.example.chronometron.ui.composables.formFields

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import com.example.chronometron.ui.composables.camera.CameraScreen
import com.example.chronometron.ui.composables.camera.ImagePreview
import com.example.chronometron.utils.buildFieldLabel

@Composable
fun ImageCapturer(
    modifier: Modifier = Modifier,
    value: Bitmap?,
    label: String,
    onChange: (Bitmap?) -> Unit = {},
    isRequired: Boolean = false,
    color: TextFieldColors? = null,
    placeholderText: String? = null,
    hasError: Boolean = false,
    errorText: MutableList<String> = mutableListOf(),
) {

    var isCameraOpen by remember { mutableStateOf(false) }
    var isImagePreviewOpen by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = buildFieldLabel(label = label, isRequired = isRequired),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
        OutlinedTextField(
            colors = color ?: OutlinedTextFieldDefaults.colors(),
            value = "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                if (value != null) {
                    IconButton(
                        onClick = { isImagePreviewOpen = true },
                        modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "View Image",
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        isCameraOpen = true
                        focusManager.clearFocus()
                    }
                },
            // Placeholder
            placeholder = {
                Text(text = if (value == null) "Capture Image" else "Image Captured" )
            },
            isError = hasError
        )

        if (hasError) {
            Text(
                text = errorText.joinToString("\n"),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
            )
        }
    }


    if (isCameraOpen) {
        CameraScreen(
            onClose = { isCameraOpen = false },
            imagePreview = value?.asImageBitmap(),
            onImagePreviewClick = { isImagePreviewOpen = true },
            onPhotoTaken = { onChange(it) }
        )
    }


    if (isImagePreviewOpen) {
        ImagePreview(
            image = value?.asImageBitmap()!!,
            onGoBack = { isImagePreviewOpen = false },
            onFinish = {
                isCameraOpen = false
                isImagePreviewOpen = false
            },
            onRemove = {
                onChange(null)
                isImagePreviewOpen = false
            })
    }
}
