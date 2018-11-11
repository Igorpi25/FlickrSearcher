package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.util.Log
import com.ivanov.tech.flickrsearcher.server.ServerMethods
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable

class SearcherPageKeyedDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val serverMethods: ServerMethods)
    : DataSource.Factory<Int, FlickrPhoto>() {

    var text:String=""

    override fun create(): DataSource<Int, FlickrPhoto> {

        Log.e("Igor Log","SearcherPageKeyedDataSourceFactory.create text="+text)

        val newsDataSource = SearcherPageKeyedDataSource(text,serverMethods, compositeDisposable)

        return newsDataSource
    }

}