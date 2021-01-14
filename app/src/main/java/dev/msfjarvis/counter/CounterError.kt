package dev.msfjarvis.counter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CounterError : Parcelable {
    @Parcelize
    object NegativeDecrement : CounterError()
}
