package muflihun.naim.memeit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import muflihun.naim.memeit.R
import muflihun.naim.memeit.activities.MemeActivity
import muflihun.naim.memeit.databinding.MemeItemBinding
import muflihun.naim.memeit.models.MemesItem

class MemesAdapter(val context: Context): RecyclerView.Adapter<MemesAdapter.ViewHolder>() {
    protected lateinit var binding: MemeItemBinding
    private var memeList: List<MemesItem> = ArrayList()

    fun setData(memeList: List<MemesItem>){
        this.memeList = memeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(root: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(root.context).inflate(R.layout.meme_item, root, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        Glide
            .with(context)
            .load(memeList[position].url)
            .into(vh.ivMeme)
        vh.ivMeme.setOnClickListener {
            val intent = Intent(context, MemeActivity::class.java)
            intent.putExtra(MemeActivity.MEME_EXTRA, memeList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return memeList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val binding: MemeItemBinding = MemeItemBinding.inflate(LayoutInflater.from(itemView.context))
        val ivMeme = itemView.findViewById<ImageView>(R.id.iv_meme)
    }
}