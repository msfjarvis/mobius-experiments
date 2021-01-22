package dev.msfjarvis.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ValidationError : Parcelable {
    @Parcelize
    object InvalidUsername : ValidationError()
    @Parcelize
    object InvalidPassword : ValidationError()
}
