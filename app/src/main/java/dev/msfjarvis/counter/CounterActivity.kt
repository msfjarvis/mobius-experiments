package dev.msfjarvis.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.spotify.mobius.Connection
import com.spotify.mobius.Mobius
import com.spotify.mobius.android.MobiusAndroid
import dev.msfjarvis.mobiusdemo.R
import kotlinx.android.synthetic.main.counter_activity.*

class CounterActivity : AppCompatActivity(R.layout.counter_activity) {
    private val loopFactory = Mobius.loop(CounterUpdate(), {
        object : Connection<Nothing> {
            override fun dispose() {
            }

            override fun accept(value: Nothing) {
            }
        }
    })

    private val controller = MobiusAndroid.controller(loopFactory, CounterModel.default())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            controller.replaceModel(savedInstanceState.getParcelable("model")!!)
        }
        controller.connect { eventConsumer ->
            object : Connection<CounterModel> {
                override fun dispose() {
                    increment_button.setOnClickListener(null)
                    decrement_button.setOnClickListener(null)
                }

                override fun accept(value: CounterModel) {
                    counter_textview.text = "${value.value}"

                    if (value.error != null) {
                        error_textview.isVisible = true
                        error_textview.text = "Cannot decrement below zero"
                    } else {
                        error_textview.isVisible = false
                    }

                    increment_button.setOnClickListener {
                        eventConsumer.accept(CounterEvent.Increment)
                    }

                    decrement_button.setOnClickListener {
                        eventConsumer.accept(CounterEvent.Decrement)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("model", controller.model)
    }
}
