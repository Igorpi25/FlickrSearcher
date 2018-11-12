package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.ivanov.tech.flickrsearcher.SearcherViewModelModule
import com.ivanov.tech.flickrsearcher.server.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

class SearcherPageKeyedDataSource (
        private val text:String)
    : PageKeyedDataSource<Int, FlickrPhoto>() {

    @Inject
    lateinit var serverMethods: ServerMethods
    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    init{
        Log.e("Igor Log","SearcherPageKeyedDataSource init")
        val scope = Toothpick.openScopes("AppScope","ViewModelScope")
        //scope.installModules(SearcherViewModelModule())
        Toothpick.inject(this, scope);
    }

    private fun searchPhotos(page:Int): Single<FlickrResponse> {

        return serverMethods.searchPhotos(ServerSettings.KEY,"relevance", "1", 10, page, 0, "photos", "json", "1",text)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, FlickrPhoto>) {

        Log.d("Igor Log","SearcherPageKeyedDataSource loadInitial text="+text);

        compositeDisposable.add(
                searchPhotos(1)
                        .map{flickrResponse -> flickrResponse.photos}
                        .map{flickrResponsePhotos->flickrResponsePhotos.photo}
                        .subscribe(
                                { listFlickrPhoto ->
                                    callback.onResult(listFlickrPhoto,
                                            null,
                                            2
                                    )
                                },
                                {
                                    Log.d("Igor Log","Error from getPhotos of Retrofit on Rx.error.callback"+it.message)
                                }
                        )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FlickrPhoto>) {
        Log.e("Igor Log","SearcherPageKeyedDataSource loadAfter params.key="+params.key+" text="+text)

        compositeDisposable.add(
                searchPhotos(params.key)
                        .map{flickrResponse -> flickrResponse.photos}
                        .map{photos->photos.photo}
                        .subscribe(
                                { listOfSinglePhotos ->
                                    callback.onResult(listOfSinglePhotos,
                                            params.key+1
                                    )
                                },
                                {
                                    Log.d("Igor Log","Error from getPhotos of Retrofit on Rx.error.callback"+it.message)
                                }
                        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FlickrPhoto>) {
        Log.e("Igor Log","SearcherPageKeyedDataSource loadBefore params.key="+params.key+" text="+text)
    }

}

