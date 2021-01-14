package dev.msfjarvis.counter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterModel(val value: Int, val error: CounterError?): Parcelable {
    val isZero: Boolean get() = value == 0

    fun increment(): CounterModel {
        return copy(value = value + 1, error = null)
    }

    fun decrement(): CounterModel {
        return copy(value = value - 1, error = null)
    }

    fun decrementError(): CounterModel {
        return copy(error = CounterError.NegativeDecrement)
    }

    companion object {
        fun default(): CounterModel {
            return CounterModel(value = 0, error = null)
        }
    }
}
