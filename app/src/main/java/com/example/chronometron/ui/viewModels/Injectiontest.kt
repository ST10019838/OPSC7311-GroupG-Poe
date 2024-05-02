//package com.example.chronometron.ui.viewModels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.chronometron.db.LocalDatabase
//import com.example.chronometron.types.Category
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//
//@HiltViewModel
//class TestViewModel @Inject constructor(private val db: LocalDatabase) : ViewModel() {
//    private val _categories = db.categories.getCategories().stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        emptyList()
//    )
//
//    val categories = _categories
//
//    fun insertData(category: Category) {
//        viewModelScope.launch {
//            db.categories.upsertCategory(category)
//        }
//    }
//
//    fun getData() {
//        viewModelScope.launch {
//            db.categories.getCategories()
//        }
//    }
//}