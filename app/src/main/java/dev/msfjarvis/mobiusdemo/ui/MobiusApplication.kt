package dev.msfjarvis.mobiusdemo.ui

import android.app.Application
import android.util.Log
import com.spotify.mobius.extras.MobiusExtras
import dev.msfjarvis.mobiusdemo.util.EventHandler

class MobiusApplication : Application() {
    private val loop = MobiusExtras.beginnerLoop(EventHandler::update).startFrom(2)

    override fun onCreate() {
        super.onCreate()
        loop.observe { counter -> Log.d("MOBIUS", "$counter") }
    }

    override fun onTerminate() {
        loop.dispose()
        super.onTerminate()
    }

    fun getLoop() = loop
}
