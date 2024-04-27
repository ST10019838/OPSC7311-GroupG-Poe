package com.example.chronometron.ui.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


// The following if statement was taken from Youtube.com
// Author: Philipp Lackner
// Link: https://www.youtube.com/watch?v=12_iKwGIP64

class MainViewModel: ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(bitmap: Bitmap){
        _bitmaps.value += bitmap
    }

//    fun fetchData(){
//        screenModelScope.launch {
//            //fetch data code
//        }
//    }
}