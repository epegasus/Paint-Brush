package dev.pegasus.paintbrush.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper

object ToolUtils {

    fun convertDrawableToBitmap(drawable: Drawable): Bitmap {
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun withDelay(delay: Long = 50, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(block, delay)
    }

}