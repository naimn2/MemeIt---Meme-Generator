package muflihun.naim.memeit.utils

import android.content.Context
import android.widget.Toast

import android.provider.MediaStore

import android.graphics.Bitmap

import android.os.Environment
import android.util.Log
import java.io.*
import java.lang.Exception
import java.util.*


class SaveImage {
    companion object {
        private const val TAG = "SaveImage"
        fun saveImage(context: Context, bitmap: Bitmap) {
            val root = context.getExternalFilesDir("Memes").toString()
            val myDir = File(root)
            myDir.mkdirs()
            val date = Date()
            val fname = "Meme-${date.seconds}_${date.minutes}_${date.hours}_${date.date}_" +
                    "${date.month}_${date.year+1900}.jpg"
            val file = File(myDir, fname)
            Log.i(TAG, "" + file)
            if (file.exists()) file.delete()
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                Toast.makeText(context, "Image Saved: ${root}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}