package dev.msfjarvis.counter

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

class CounterUpdate : Update<CounterModel, CounterEvent, Nothing> {
    override fun update(model: CounterModel, event: CounterEvent): Next<CounterModel, Nothing> {
        return when (event) {
            CounterEvent.Increment -> next(model.increment())
            CounterEvent.Decrement -> {
                if (model.isZero) {
                    next(model.decrementError())
                } else {
                    next(model.decrement())
                }
            }
        }
    }
}
