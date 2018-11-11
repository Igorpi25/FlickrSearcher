package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ivanov.tech.flickrsearcher.server.ServerMethods
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable

class SearcherPageKeyedDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val serverMethods: ServerMethods)
    : DataSource.Factory<Int, FlickrPhoto>() {

    private val newsDataSourceLiveData = MutableLiveData<SearcherPageKeyedDataSource>()

    override fun create(): DataSource<Int, FlickrPhoto> {
        val newsDataSource = SearcherPageKeyedDataSource(serverMethods, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)

        return newsDataSource
    }
}