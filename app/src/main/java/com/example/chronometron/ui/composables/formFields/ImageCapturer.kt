package com.example.chronometron.ui.composables.formFields

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import ch.benlu.composeform.Field
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import com.example.chronometron.ui.composables.camera.CameraScreen
import com.example.chronometron.ui.composables.camera.ImagePreview

//@Composable
class ImageCapturer(
    label: String,
    form: Form,
    modifier: Modifier? = Modifier,
    fieldState: FieldState<Bitmap?>,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    changed: ((v: Bitmap?) -> Unit)? = null
) : Field<Bitmap?>(
    label = label,
    form = form,
    fieldState = fieldState,
    isEnabled = isEnabled,
    modifier = modifier,
    imeAction = imeAction,
    keyboardType = keyboardType,
    visualTransformation = visualTransformation,
    changed = changed
) {

    @SuppressLint("NotConstructor")
    @Composable
    override fun Field() {
        var isCameraOpen by remember { mutableStateOf(false) }
        var isImagePreviewOpen by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        Column(modifier = modifier ?: Modifier) {
            OutlinedTextField(
                value = if (fieldState.state.value == null) "Capture Image" else "Image Captured",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    if (fieldState.state.value != null) {
                        IconButton(
                            onClick = { isImagePreviewOpen = true },
                            modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
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
                    }
            )

            if (fieldState.hasError()) {
                Text(
                    text = fieldState.errorText.joinToString("\n"),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
                )
            }
        }


        if (isCameraOpen) {
            CameraScreen(
                onClose = { isCameraOpen = false },
                imagePreview = fieldState.state.value?.asImageBitmap(),
                onImagePreviewClick = { isImagePreviewOpen = true },
                onPhotoTaken = { onChange(it, form) }
            )
        }


        if (isImagePreviewOpen) {
            ImagePreview(
                image = fieldState.state.value?.asImageBitmap()!!,
                onGoBack = { isImagePreviewOpen = false },
                onFinish = {
                    isCameraOpen = false
                    isImagePreviewOpen = false
                },
                onRemove = {
                    onChange(null, form)
                    isImagePreviewOpen = false
                })
        }

    }
}