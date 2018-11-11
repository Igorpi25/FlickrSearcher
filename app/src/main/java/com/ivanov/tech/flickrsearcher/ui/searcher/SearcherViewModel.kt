package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.ivanov.tech.flickrsearcher.App.Companion.prefs
import com.ivanov.tech.flickrsearcher.server.ServerMethodsProvider
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import io.reactivex.disposables.CompositeDisposable

class SearcherViewModel : ViewModel() {

    private val pageSize = 10

    var photoPagedList: LiveData<PagedList<FlickrPhoto>>

    private val compositeDisposable = CompositeDisposable()

    private val searcherPageKeyedDataSourceFactory: SearcherPageKeyedDataSourceFactory

    init {
        searcherPageKeyedDataSourceFactory = SearcherPageKeyedDataSourceFactory(compositeDisposable, ServerMethodsProvider.serverMethods)

        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                //.setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()

        photoPagedList = LivePagedListBuilder<Int, FlickrPhoto>(searcherPageKeyedDataSourceFactory, config).build()



    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
