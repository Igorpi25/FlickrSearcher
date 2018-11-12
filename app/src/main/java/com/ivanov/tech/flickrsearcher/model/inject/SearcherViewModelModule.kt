package com.ivanov.tech.flickrsearcher.model.inject

import android.util.Log
import com.ivanov.tech.flickrsearcher.model.repository.SuggestionsRepository
import com.ivanov.tech.flickrsearcher.model.repository.ServerRepository
import io.reactivex.disposables.CompositeDisposable
import toothpick.config.Module

class SearcherViewModelModule: Module() {
    init {
        Log.e("Igor log", "SearcherViewModelModule init")
        bind(SuggestionsRepository::class.java).toProviderInstance(SuggestionsRepositoryProvider())
        bind(ServerRepository::class.java).toProviderInstance(ServerMethodsProvider())
        bind(CompositeDisposable::class.java).toInstance(CompositeDisposable())
    }
}