package muflihun.naim.memeit.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import muflihun.naim.memeit.R
import muflihun.naim.memeit.databinding.ActivityMemeBinding
import muflihun.naim.memeit.models.MemesItem
import muflihun.naim.memeit.utils.ImageEditor
import muflihun.naim.memeit.utils.SaveImage
import java.io.IOException

class MemeActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE_CODE: Int = 1
    private lateinit var binding: ActivityMemeBinding
    private lateinit var meme: MemesItem

    companion object {
        private val TAG = "MemeActivity"
        val MEME_EXTRA = "memeExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarEditmeme)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnMemeAddtext.setOnClickListener { addTextHandler() }
        binding.btnMemeAddimg.setOnClickListener { addImageHandler() }
        binding.includedTextInserter.btnTextinserterCancel.setOnClickListener {
            showTextInserter(false)
        }
        binding.includedTextInserter.btnTextinserterAdd.setOnClickListener { addFinalTextHandler() }

        meme = intent.getParcelableExtra<MemesItem>(MEME_EXTRA)!!
        renderImage()
//        DragableViewMaker.makeViewDragable(binding.etMemeAddtext)
    }

    private fun renderImage() {
        Glide
            .with(this)
            .load(meme.url)
            .into(binding.ivActMeme)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meme_editor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_item_save_meme -> {
                val bitmap: Bitmap = getBitmapFromImage(binding.ivActMeme)
                SaveImage.saveImage(this, bitmap)
            }
            R.id.menu_item_reset_meme -> { TODO() }
            R.id.menu_item_share_meme -> { TODO() }
        }
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    /**
     * Add Text Handling
     *
     * */
    
    private fun getBitmapFromImage(imageView: ImageView): Bitmap { // Pemborosan harusnya bisa ji 1 kali saja ini
        return (imageView.drawable as BitmapDrawable).bitmap
    }

    private fun addTextHandler() {
        showTextInserter(true)
    }

    private fun addFinalTextHandler() {
        val text = binding.includedTextInserter.etTextinserterAddtext.text.toString()
        var bitmap = getBitmapFromImage(binding.ivActMeme)

        ImageEditor.listenImageViewTouchCoordinate(binding.ivActMeme,
            object : ImageEditor.OnTouchCallback {
                override fun onTouch(x: Float, y: Float) {
                    writeTextOnImage(bitmap, text, x, y)
                    ImageEditor.stopListenImageViewTouchCoordinate(binding.ivActMeme)
                    isLocating(false, "")
                    binding.includedTextInserter.etTextinserterAddtext.setText(getString(R.string.type_here))
                }
            })
        isLocating(true, getString(R.string.set_text_location))
    }

    private fun writeTextOnImage(bitmap: Bitmap, text: String, x: Float, y: Float) {
        val size = binding.includedTextInserter.etTextinserterAddtext.textSize
        val newBitmap = ImageEditor.writeTextOnImage(bitmap, text, size, Color.BLACK, x, y)

        Glide.with(this).load(newBitmap).into(binding.ivActMeme)
        showTextInserter(false)
    }

    private fun showTextInserter(state: Boolean) {
        if (state) {
            binding.includedTextInserter.root.visibility = View.VISIBLE
            binding.includedTextInserter.etTextinserterAddtext.requestFocus()
            binding.llMemeeditBar.visibility = View.INVISIBLE
        } else {
            binding.includedTextInserter.root.visibility = View.GONE
            binding.llMemeeditBar.visibility = View.VISIBLE
        }
    }

    private fun isLocating(state: Boolean, message: String) {
        binding.tvSetLocationMessage.text = message
        if (state) {
            binding.tvSetLocationMessage.visibility = View.VISIBLE
            binding.includedTextInserter.root.visibility = View.GONE
            binding.llMemeeditBar.visibility = View.INVISIBLE
        } else {
            binding.tvSetLocationMessage.visibility = View.GONE
            binding.llMemeeditBar.visibility = View.VISIBLE
        }
    }

    /**
     * Add Image Handling
     *
     */

    private fun addImageHandler() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSE_IMAGE_CODE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val frontBitmap: Bitmap
        if (requestCode == CHOOSE_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            val fileUri: Uri? = data.data
            try {
                frontBitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
                val backBitmap = getBitmapFromImage(binding.ivActMeme)

                ImageEditor.listenImageViewTouchCoordinate(binding.ivActMeme,
                    object: ImageEditor.OnTouchCallback {
                        override fun onTouch(x: Float, y: Float) {
                            combineImage(backBitmap, frontBitmap, x, y)
                            ImageEditor.stopListenImageViewTouchCoordinate(binding.ivActMeme)
                            isLocating(false, "")
                        }
                    })
                isLocating(true, getString(R.string.set_image_location))
            } catch (e: IOException) {
                Log.e(TAG, "onActivityResult: $e", e)
            }
        }
    }

    private fun combineImage(backBitmap: Bitmap, frontBitmap: Bitmap, x:Float, y:Float) {
        val mergedBitmap = ImageEditor.mergeImages(backBitmap, frontBitmap, x, y)
        Glide.with(this).load(mergedBitmap).into(binding.ivActMeme)
    }
}