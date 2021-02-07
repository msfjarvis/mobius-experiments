package dev.msfjarvis.apod.data.model

import androidx.annotation.VisibleForTesting
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
) {
    internal companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal fun testFake(): PictureDetail {
            return PictureDetail(
                copyright = "ESA/HubbleNASA",
                date = "2019-12-01",
                explanation = "Why does this galaxy have a ring of bright blue stars?  Beautiful island universe Messier 94 lies a mere 15 million light-years distant in the northern constellation of the Hunting Dogs (Canes Venatici). A popular target for Earth-based astronomers, the face-on spiral galaxy is about 30,000 light-years across, with spiral arms sweeping through the outskirts of its broad disk. But this Hubble Space Telescope field of view spans about 7,000 light-years across M94's central region. The featured close-up highlights the galaxy's compact, bright nucleus, prominent inner dust lanes, and the remarkable bluish ring of young massive stars. The ring stars are all likely less than 10 million years old, indicating that M94 is a starburst galaxy that is experiencing an epoch of rapid star formation from inspiraling gas. The circular ripple of blue stars is likely a wave propagating outward, having been triggered by the gravity and rotation of a oval matter distributions. Because M94 is relatively nearby, astronomers can better explore details of its starburst ring.    Astrophysicists: Browse 2,000+ codes in the Astrophysics Source Code Library",
                hdUrl = "https://apod.nasa.gov/apod/image/1912/M94_Hubble_1002.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Starburst Galaxy M94 from Hubble",
                url = "https://apod.nasa.gov/apod/image/1912/M94_Hubble_960.jpg",
            )
        }
    }
}
