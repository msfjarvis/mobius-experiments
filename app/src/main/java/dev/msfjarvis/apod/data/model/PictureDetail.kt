package dev.msfjarvis.apod.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class represents a single picture as sent by the NASA APOD API.
 */
@JsonClass(generateAdapter = true)
data class PictureDetail(
    val copyright: String? = null,
    val date: String,
    val explanation: String,
    @Json(name = "hdurl")
    val hdUrl: String,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "service_version")
    val serviceVersion: String,
    val title: String,
    val url: String,
)
