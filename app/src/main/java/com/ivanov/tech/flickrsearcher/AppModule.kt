package com.ivanov.tech.flickrsearcher

import android.accounts.AccountManager
import android.app.Application
import android.util.Log
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule
import toothpick.smoothie.provider.AccountManagerProvider

class AppModule(application: Application) : SmoothieApplicationModule(application,"com.ivanov.tech.flickrsearcher.prefs") {
    init {
        Log.e("Igor log", "AppModule init")
        bind(SuggestionsRepository::class.java).toProviderInstance(SuggestionsRepositoryProvider(application))

    }
}