package dev.msfjarvis.apod

import com.spotify.mobius.Next
import com.spotify.mobius.Next.noChange
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Update

class GalleryUpdate : Update<GalleryModel, GalleryEvent, GalleryEffect> {
    override fun update(
        model: GalleryModel,
        event: GalleryEvent
    ): Next<GalleryModel, GalleryEffect> {
        return when (event) {
            is GalleryEvent.PictureClicked -> dispatch(setOf(GalleryEffect.LaunchDetails(event.picture)))
            else -> noChange()
        }
    }
}
