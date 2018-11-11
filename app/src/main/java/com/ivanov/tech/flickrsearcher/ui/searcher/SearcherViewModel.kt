package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.*
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.ivanov.tech.flickrsearcher.App.Companion.prefs
import com.ivanov.tech.flickrsearcher.server.ServerMethodsProvider
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList

class SearcherViewModel : ViewModel() {

    private val pageSize = 10

    var photoPagedList: LiveData<PagedList<FlickrPhoto>>
    var suggestions : MutableLiveData<ArrayList<String>> = MutableLiveData()

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

        suggestions.postValue(prefs.suggestions as ArrayList<String>)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun setText(text:String){
        searcherPageKeyedDataSourceFactory.text = text
        photoPagedList.value?.dataSource?.invalidate()
    }
}
