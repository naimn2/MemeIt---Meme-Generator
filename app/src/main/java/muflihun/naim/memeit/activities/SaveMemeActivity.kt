package muflihun.naim.memeit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import muflihun.naim.memeit.R
import muflihun.naim.memeit.databinding.ActivitySaveMemeBinding

class SaveMemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveMemeBinding

    companion object {
        const val BITMAP_SAVE_MEME_EXTRA = "bitmapSaveMemeExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveMemeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}