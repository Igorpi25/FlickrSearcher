package com.ivanov.tech.flickrsearcher.model.inject

import android.app.Application
import android.util.Log
import toothpick.smoothie.module.SmoothieApplicationModule

class AppModule(application: Application) : SmoothieApplicationModule(application,"com.ivanov.tech.flickrsearcher.prefs") {
    init {
        Log.e("Igor log", "AppModule init")
    }
}