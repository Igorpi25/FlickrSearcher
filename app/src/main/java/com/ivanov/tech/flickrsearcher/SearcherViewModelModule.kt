package com.ivanov.tech.flickrsearcher

import android.accounts.AccountManager
import android.app.Application
import android.util.Log
import com.ivanov.tech.flickrsearcher.server.ServerMethods
import com.ivanov.tech.flickrsearcher.server.ServerMethodsProvider
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.smoothie.module.SmoothieApplicationModule
import toothpick.smoothie.provider.AccountManagerProvider

class SearcherViewModelModule: Module() {
    init {
        Log.e("Igor log", "SearcherViewModelModule init")
        bind(SuggestionsRepository::class.java).toProviderInstance(SuggestionsRepositoryProvider())
        bind(ServerMethods::class.java).toProviderInstance(ServerMethodsProvider())
        bind(CompositeDisposable::class.java).toInstance(CompositeDisposable())
    }
}