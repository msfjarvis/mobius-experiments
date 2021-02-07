package dev.msfjarvis.apod.data.local

import android.content.Context
import com.squareup.moshi.JsonAdapter
import dev.msfjarvis.apod.data.model.PictureDetail
import dev.msfjarvis.apod.data.remote.ApodApi
import dev.msfjarvis.mobiusdemo.R

class ApodLocalApi constructor(
    private val context: Context,
    private val jsonAdapter: JsonAdapter<List<PictureDetail>>,
): ApodApi {
    override fun getImages(): List<PictureDetail> {
        val resource = context.resources.openRawResource(R.raw.data)
        val string = resource.bufferedReader().use { it.readText() }
        return jsonAdapter.fromJson(string) ?: error("Failed to parse JSON data")
    }
}
