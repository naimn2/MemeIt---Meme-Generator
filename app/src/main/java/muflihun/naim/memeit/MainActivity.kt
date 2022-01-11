package muflihun.naim.memeit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import muflihun.naim.memeit.adapters.MemesAdapter
import muflihun.naim.memeit.databinding.ActivityMainBinding
import muflihun.naim.memeit.models.MemeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var memesAdapter: MemesAdapter
    companion object {
        private val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupMemeList()
        loadMemes()
        setupRefreshListener()
    }

    private fun loadMemes() {
        showLoading(true)
        val client = RetroConfig.getApiService().getMemes()
        client.enqueue(object: Callback<MemeResponse>{
            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onResponse: length: ${responseBody.data.memes.size}")
                        memesAdapter.setData(responseBody.data.memes)
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<MemeResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${p1.message}")
            }

        })
    }

    private fun setupMemeList() {
        memesAdapter = MemesAdapter(this)
        binding.rvMemes.adapter = memesAdapter
        binding.rvMemes.layoutManager = GridLayoutManager(this, 3)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            if (binding.refreshLayout.isRefreshing) {
                binding.pbMain.visibility = View.GONE
                binding.rvMemes.visibility = View.GONE
            } else {
                binding.pbMain.visibility = View.VISIBLE
                binding.rvMemes.visibility = View.GONE
            }
        } else {
            binding.pbMain.visibility = View.GONE
            binding.rvMemes.visibility = View.VISIBLE
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun setupRefreshListener() {
        binding.refreshLayout.setColorSchemeResources(R.color.teal_700,R.color.teal_200);
        binding.refreshLayout.setOnRefreshListener {
            loadMemes()
        }
    }
}