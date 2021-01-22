package dev.msfjarvis.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Username(val username: String): Parcelable {
    fun validate(): List<ValidationError> {
        return if (username.isEmpty()) {
            listOf(ValidationError.InvalidUsername)
        } else {
            emptyList()
        }
    }
    companion object {
        val BLANK = Username("")
    }
}
