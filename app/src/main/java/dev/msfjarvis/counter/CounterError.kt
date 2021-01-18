package dev.msfjarvis.counter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CounterError : Parcelable {
    @Parcelize
    object NegativeDecrement : CounterError()
}
