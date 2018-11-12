package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.*
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.ivanov.tech.flickrsearcher.AppModule
import com.ivanov.tech.flickrsearcher.SearcherViewModelModule
import com.ivanov.tech.flickrsearcher.SuggestionsRepository
import com.ivanov.tech.flickrsearcher.server.ServerMethodsProvider
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

class SearcherViewModel : ViewModel() {


    @Inject
    lateinit var suggestionsRepository : SuggestionsRepository

    private val pageSize = 10

    private val searcherPageKeyedDataSourceFactory: SearcherPageKeyedDataSourceFactory

    private val compositeDisposable = CompositeDisposable()

    var pagedList: LiveData<PagedList<FlickrPhoto>>

    val suggestionsList:LiveData<List<String>>
        get(){
            return suggestionsRepository.getList()
        }


    var searchedText:String=""
        set(text:String){
            searcherPageKeyedDataSourceFactory.text = text
            pagedList.value?.dataSource?.invalidate()

            suggestionsRepository.put(text)
        }

    init {

        val scope = Toothpick.openScopes("AppScope","ViewModelScope")
        scope.installModules(SearcherViewModelModule())
        Toothpick.inject(this, scope);


        searcherPageKeyedDataSourceFactory = SearcherPageKeyedDataSourceFactory(compositeDisposable, ServerMethodsProvider.serverMethods)

        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                //.setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()

        pagedList = LivePagedListBuilder<Int, FlickrPhoto>(searcherPageKeyedDataSourceFactory, config).build()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }




}
