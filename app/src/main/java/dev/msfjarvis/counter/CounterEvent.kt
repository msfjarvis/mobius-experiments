package dev.msfjarvis.counter

sealed class CounterEvent {
    object Increment : CounterEvent()
    object Decrement : CounterEvent()
}
