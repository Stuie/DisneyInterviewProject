package com.example.marvel

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
    }
}
