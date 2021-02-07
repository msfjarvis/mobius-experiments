package dev.msfjarvis.apod

import com.spotify.mobius.test.NextMatchers.hasEffects
import com.spotify.mobius.test.NextMatchers.hasNoModel
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import dev.msfjarvis.apod.data.model.PictureDetail
import org.junit.Test

class GalleryUpdateTest {

    private val initialModel = GalleryModel.default()
    private val updateSpec = UpdateSpec(GalleryUpdate())

    @Test
    fun `when image in gallery is clicked then launch detail view`() {
        val picture = PictureDetail.testFake()

        updateSpec
            .given(initialModel)
            .whenEvent(GalleryEvent.PictureClicked(picture))
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(GalleryEffect.LaunchDetails(picture)),
                )
            )

    }
}
