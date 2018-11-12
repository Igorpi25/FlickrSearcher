package com.ivanov.tech.flickrsearcher

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.configuration.Configuration


const val EXTRA_URL = "com.ivanov.tech.flickrsearcher.URL"
const val EXTRA_TITLE = "com.ivanov.tech.flickrsearcher.TITLE"


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Toothpick.setConfiguration(if(BuildConfig.DEBUG)Configuration.forDevelopment()else Configuration.forProduction())

        val appScope = Toothpick.openScope("AppScope")
        appScope.installModules(AppModule(this))
        Toothpick.inject(this, appScope)
    }

}

