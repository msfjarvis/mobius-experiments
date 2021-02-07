package dev.msfjarvis.apod

import dev.msfjarvis.apod.data.model.PictureDetail

sealed class GalleryEvent {
    data class PictureClicked(val picture: PictureDetail) : GalleryEvent()
}
