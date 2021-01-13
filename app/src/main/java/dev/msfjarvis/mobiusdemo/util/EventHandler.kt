package dev.msfjarvis.mobiusdemo.util

import dev.msfjarvis.mobiusdemo.data.Event

object EventHandler {
    fun update(
        counter: Int,
        event: Event,
    ): Int {
        return when (event) {
            Event.UP -> counter + 1
            Event.DOWN -> if (counter > 0) counter - 1 else counter
        }
    }
}
