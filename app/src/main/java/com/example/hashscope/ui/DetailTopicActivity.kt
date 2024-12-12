package com.example.hashscope.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hashscope.R
import com.example.hashscope.model.PlatformData
import com.example.hashscope.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTopicActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DetailTopicActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_topic)

        // Ambil data dari Intent
        val categoryId = intent.getIntExtra("CATEGORY_ID", 0)
        val mainTopicId = intent.getIntExtra("MAIN_TOPIC_ID", 0)
        val xDatasetsId = intent.getIntExtra("X_DATASETS_ID", -1)
        val webDatasetsId = intent.getIntExtra("WEB_DATASETS_ID", -1)
        val youtubeDatasetsId = intent.getIntExtra("YOUTUBE_DATASETS_ID", -1)


        // Tentukan platform berdasarkan ID yang tersedia
        val platform = when {
            xDatasetsId != -1 -> "X"
            webDatasetsId != -1 -> "Web"
            youtubeDatasetsId != -1 -> "YouTube"
            else -> {
                Log.e(TAG, "No valid platform ID found.")
                Toast.makeText(this, "No platform data available", Toast.LENGTH_SHORT).show()
                return // Hentikan aktivitas jika tidak ada data platform
            }
        }

        Log.d(TAG, "Selected platform: $platform")

        // Panggil API untuk mendapatkan detail data
        fetchPlatformData(platform, categoryId, mainTopicId)
    }

    private fun fetchPlatformData(platform: String, categoryId: Int, mainTopicId: Int) {
        val call = RetrofitClient.apiService.getPlatformData(platform, categoryId, mainTopicId)
        call.enqueue(object : Callback<List<PlatformData>> {
            override fun onResponse(
                call: Call<List<PlatformData>>,
                response: Response<List<PlatformData>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        Log.d(TAG, "Data fetched successfully: $data")
                        navigateToFragment(platform, data[0])
                    } else {
                        Log.w(TAG, "No data found for platform: $platform")
                        Toast.makeText(
                            this@DetailTopicActivity,
                            "No data found for platform: $platform",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.e(TAG, "Failed to fetch data: ${response.message()}")
                    Toast.makeText(
                        this@DetailTopicActivity,
                        "Failed to fetch data: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<PlatformData>>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}")
                Toast.makeText(
                    this@DetailTopicActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun navigateToFragment(platform: String, data: PlatformData) {
        Log.d(TAG, "Navigating to fragment for platform: $platform")

        val fragment: Fragment = when (platform) {
            "X" -> DetailXFragment.newInstance(data)
            "Web" -> DetailWebFragment.newInstance(data)
            "YouTube" -> DetailYoutubeFragment.newInstance(data)
            else -> {
                Log.e(TAG, "Unsupported platform: $platform")
                throw IllegalArgumentException("Unsupported platform: $platform")
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
