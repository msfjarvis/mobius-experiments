package dev.msfjarvis.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.spotify.mobius.Connection
import com.spotify.mobius.Mobius
import com.spotify.mobius.android.MobiusAndroid
import dev.msfjarvis.mobiusdemo.R
import dev.msfjarvis.mobiusdemo.databinding.CounterActivityBinding

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
    private val binding by viewBinding(CounterActivityBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            controller.replaceModel(savedInstanceState.getParcelable("model")!!)
        }
        controller.connect { eventConsumer ->
            object : Connection<CounterModel> {
                override fun dispose() {
                    binding.incrementButton.setOnClickListener(null)
                    binding.decrementButton.setOnClickListener(null)
                }

                override fun accept(value: CounterModel) {
                    binding.counterTextview.text = "${value.value}"

                    if (value.error != null) {
                        binding.errorTextview.isVisible = true
                        binding.errorTextview.text = getString(R.string.error_decrement_below_zero)
                    } else {
                        binding.errorTextview.isVisible = false
                    }

                    binding.incrementButton.setOnClickListener {
                        eventConsumer.accept(CounterEvent.Increment)
                    }

                    binding.decrementButton.setOnClickListener {
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
