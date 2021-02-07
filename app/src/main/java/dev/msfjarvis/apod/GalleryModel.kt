package dev.msfjarvis.apod

import dev.msfjarvis.apod.data.model.PictureDetail

data class GalleryModel(
    val pictures: List<PictureDetail>,
) {

    companion object {
        fun default(): GalleryModel {
            return GalleryModel(
                emptyList()
            )
        }
    }
}
