package muflihun.naim.memeit.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView


class ImageEditor {
    companion object {
        fun writeTextOnImage(bitmap: Bitmap,
                             text: String,
                             size: Float,
                             color: Int,
                             x: Float,
                             y: Float
        ): Bitmap {
            val canvas = Canvas(bitmap)
            val paint = Paint()
            paint.color = color
            paint.textSize = size
            val width = text.length*size/4
            val height = size/4
            canvas.drawText(text, x-width, y+height, paint)
            canvas.save()
            return bitmap
        }

        @SuppressLint("ClickableViewAccessibility")
        fun listenImageViewTouchCoordinate(iv: ImageView, onTouchCallback: OnTouchCallback): Array<Float> {
            var x: Float = 0.0f
            var y: Float = 0.0f
            iv.setOnTouchListener { _, event ->
                x = event?.x!!
                y = event?.y!!
                Log.d("ImageEditor", "getImageViewTouchedCoordinate: ($x, $y)")
                onTouchCallback.onTouch(x, y)
                true
            }
            return arrayOf(x, y)
        }

        @SuppressLint("ClickableViewAccessibility")
        fun stopListenImageViewTouchCoordinate(iv: ImageView) {
            iv.setOnTouchListener(null)
        }

        fun mergeImages(back: Bitmap, front: Bitmap, x: Float, y: Float): Bitmap? {
            val result = Bitmap.createBitmap(back.width, back.height, back.config)
            val canvas = Canvas(result)
//            val widthBack = back.width
//            val widthFront = front.width
//            val move = ((widthBack - widthFront) / 2).toFloat()
            val newX = x - front.width / 2f
            val newY = y - front.height / 2f
            canvas.drawBitmap(back, 0f, 0f, null)
            canvas.drawBitmap(front, newX, newY, null)
            return result
        }
    }

    interface OnTouchCallback {
        fun onTouch(x: Float, y: Float)
    }
}