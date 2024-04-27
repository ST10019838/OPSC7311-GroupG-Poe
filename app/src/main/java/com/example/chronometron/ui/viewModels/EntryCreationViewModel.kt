package com.example.chronometron.ui.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EntryCreationViewModel : ViewModel() {
    var isDialogOpen by mutableStateOf(false)
    var isCameraOpen by mutableStateOf(false)
    var isImagePreviewOpen by mutableStateOf(false)

    var savedImage: Bitmap? by mutableStateOf(null)

    fun saveImage(bitmap: Bitmap){
        savedImage = bitmap
    }

    fun showImagePreview(){
        isImagePreviewOpen = true
    }

    fun closeImagePreview(){
        isImagePreviewOpen = false
    }

    fun closeCamera(){
        isCameraOpen = false
    }
}

//class EntryCreationViewModel : ScreenModel {
//    var isDialogOpen by mutableStateOf(false)
//
//
//
//}