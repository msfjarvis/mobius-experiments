package dev.msfjarvis.mobiusdemo.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dev.msfjarvis.mobiusdemo.data.Event

class EmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).post {
            val loop = (application as MobiusApplication).getLoop()
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.DOWN)
            loop.dispatchEvent(Event.UP)
            loop.dispatchEvent(Event.DOWN)
        }
    }
}
