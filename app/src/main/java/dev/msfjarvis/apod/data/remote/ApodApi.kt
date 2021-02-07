package dev.msfjarvis.apod.data.remote

import dev.msfjarvis.apod.data.model.PictureDetail

interface ApodApi {
    fun getImages(): List<PictureDetail>
}
