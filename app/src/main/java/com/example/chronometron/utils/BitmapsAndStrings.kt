package com.example.chronometron.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

// The following code was adapted from medium.com
// Author: Anil Kr Mourya
// Link: https://medium.com/@mr.appbuilder/how-to-convert-base64-string-to-bitmap-and-bitmap-to-base64-string-7a30947b0494


// convert Image bitmap to Base64
fun BitmaptoString(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}


// convert Base64 to Image bitmap
fun StringtoBitmap(base64String: String): Bitmap {
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}