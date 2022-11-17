package com.example.microglucometer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class ImageConversion {
    fun encodeImageFromUri(context: Context, uri: Uri): String {
        val byteArrayOutputStream = ByteArrayOutputStream()

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100,
            byteArrayOutputStream as OutputStream,
        )
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun getBitmapImage(str: String): Bitmap {
        val data = Base64.decode(str, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(
            data,
            0,
            data.size,
        )
    }

}