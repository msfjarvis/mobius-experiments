package dev.msfjarvis.counter

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CounterUpdateTest {

    private val initialModel = CounterModel.default()
    private val updateSpec = UpdateSpec(CounterUpdate())

    @Test
    fun `when increment event occurs value is increment`() {
        updateSpec
            .given(initialModel)
            .whenEvent(CounterEvent.Increment)
            .then(assertThatNext(
                hasModel(initialModel.increment()),
                hasNoEffects(),
            ))
    }

    @Test
    fun `when decrement event occurs value is decremented`() {
        val model = initialModel.increment()
        updateSpec
            .given(model)
            .whenEvent(CounterEvent.Decrement)
            .then(assertThatNext(
                hasModel(model.decrement()),
                hasNoEffects(),
            ))
    }

    @Test
    fun `decrementing to negative values show error`() {
        updateSpec
            .given(initialModel)
            .whenEvent(CounterEvent.Decrement)
            .then(assertThatNext(
                hasModel(initialModel.decrementError()),
                hasNoEffects(),
            ))
    }
}
