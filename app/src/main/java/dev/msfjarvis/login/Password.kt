package dev.msfjarvis.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Password(val password: String): Parcelable {
    fun validate(): List<ValidationError> {
        return if (password.isEmpty()) {
            listOf(ValidationError.InvalidPassword)
        } else {
            emptyList()
        }
    }
    companion object {
        val BLANK = Password("")
    }
}
