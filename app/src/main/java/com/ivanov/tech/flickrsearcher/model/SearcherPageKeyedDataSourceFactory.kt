package com.ivanov.tech.flickrsearcher.model

import android.arch.paging.DataSource
import android.util.Log
import com.ivanov.tech.flickrsearcher.model.entity.FlickrPhoto

class SearcherPageKeyedDataSourceFactory
    : DataSource.Factory<Int, FlickrPhoto>() {

    var text:String=""

    override fun create(): DataSource<Int, FlickrPhoto> {

        Log.e("Igor Log","SearcherPageKeyedDataSourceFactory.create text="+text)

        val newsDataSource = SearcherPageKeyedDataSource(text)

        return newsDataSource
    }

}