//package com.example.chronometron.db
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import androidx.room.TypeConverter
//import com.chargemap.compose.numberpicker.FullHours
//import com.chargemap.compose.numberpicker.Hours
//import com.example.chronometron.types.Category
//import java.util.Date
//import kotlin.io.encoding.Base64
//import kotlin.io.encoding.ExperimentalEncodingApi
//
//class Converters {
//    @TypeConverter
//    fun stringToDate(string: String?): Date? {
//        return Date(string)
//    }
//
//    @TypeConverter
//    fun dateToString(date: Date?): String? {
//        return date.toString()
//    }
//
//    @TypeConverter
//    fun stringToHours(string: String?): Hours? {
//        val values = string?.split(" ")
//        return FullHours(
//            hours = values?.get(0)?.toInt() ?: 0,
//            minutes = values?.get(1)?.toInt() ?: 0
//        )
//    }
//
//    @TypeConverter
//    fun hoursToString(hours: Hours?): String? {
//        return "${hours?.hours} ${hours?.minutes}"
//    }
//
//    @TypeConverter
//    fun stringToCategory(string: String?): Category? {
//        val values = string?.split(" ")
//        return Category(
//            id = values?.get(0)?.toInt() ?: 0,
//            name = (values?.get(1) ?: 0).toString()
//        )
//    }
//
//    @TypeConverter
//    fun categoryToString(category: Category): String? {
//        return "${category.id} ${category?.name}"
//    }
//
//    @OptIn(ExperimentalEncodingApi::class)
//    @TypeConverter
//    fun stringToBitmap(string: String?): Bitmap? {
//        val imageBytes = string?.let { Base64?.decode(it, 0) }
//        return imageBytes?.let { BitmapFactory.decodeByteArray(imageBytes, 0, it.size) }
//    }
//
//    @TypeConverter
//    fun bitmapToString(bitmap: Bitmap?): String? {
//        return "${bitmap?.toString()}"
//    }
//}