package com.slide.show

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

class SlideShowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        App = this
    }

    companion object {
        private var App: SlideShowApp? = null
        fun getApplication(): Context {
            return App!!.applicationContext
        }
    }
}
