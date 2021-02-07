package dev.msfjarvis.apod

import dev.msfjarvis.apod.data.model.PictureDetail

sealed class GalleryEffect {
    data class LaunchDetails(val picture: PictureDetail) : GalleryEffect()
}
