package muflihun.naim.memeit

import muflihun.naim.memeit.models.MemeResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetroService {
    @GET("get_memes")
    fun getMemes(): Call<MemeResponse>
}