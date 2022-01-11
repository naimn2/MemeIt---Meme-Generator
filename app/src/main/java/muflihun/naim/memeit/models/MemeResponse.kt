package muflihun.naim.memeit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemeResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean
) : Parcelable {
}

@Parcelize
data class Data(

	@field:SerializedName("memes")
	val memes: List<MemesItem>
) : Parcelable {
}

@Parcelize
data class MemesItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("box_count")
	val boxCount: Int
) : Parcelable {
}
