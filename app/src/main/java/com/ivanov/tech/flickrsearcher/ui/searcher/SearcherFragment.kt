package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.ivanov.tech.flickrsearcher.*
import kotterknife.bindView
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import toothpick.Toothpick


class SearcherFragment : Fragment() {

    val recyclerView : RecyclerView by bindView(R.id.recyclerView)
    val search : AutoCompleteTextView by bindView(R.id.search)

    lateinit var viewModel: SearcherViewModel
    lateinit var searcherPagedListAdapter : SearcherPagedListAdapter

    companion object {
        fun newInstance() = SearcherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        Log.e("Igor Log","SearcherFragment.onCreateView")

        val view=inflater.inflate(R.layout.fragment_searcher, container, false)

        val appScope = Toothpick.openScopes("AppScope","FragmentScope")
        Toothpick.inject(this, appScope);

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.e("Igor Log","SearcherFragment.onActivityCreated")

        viewModel = ViewModelProviders.of(this).get(SearcherViewModel::class.java)
        // TODO: Use the ViewModel
        setupPagedList()
        setupAutoCompleteEditText()
    }

    private fun setupPagedList() {
        Log.e("Igor Log","SearcherFragment.setupPagedList")

        searcherPagedListAdapter = SearcherPagedListAdapter(::photoItemClicked)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.adapter = searcherPagedListAdapter


        viewModel.pagedList.observe(this, Observer {
            Log.e("Igor log","SearcherFragment.setupPagedList observer called pagedlis.size="+it?.size)
            searcherPagedListAdapter.submitList(it)
        })

    }

    private fun setupAutoCompleteEditText(){

        Log.e("Igor Log","SearcherFragment.setupAutoCompleteEditText")

        val arrayAdapter=ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)// Create the adapter and set it to the AutoCompleteTextView
        search.setAdapter(arrayAdapter)

        viewModel.suggestionsList.observe(this, Observer{
            Log.e("Igor log","SearcherFragment.setupAutoCompleteEditText observer called list.size="+it?.size)
            arrayAdapter.clear()
            arrayAdapter.addAll(it)
        })


        search.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER){

                val text=search.text.toString()
                viewModel.searchedText=text

                true
            }else{
                false
            }
        }
    }


    fun startFullscreenActivity(flicckrPhoto: FlickrPhoto){

        Log.e("Igor Log","SearcherFragment.startFullscreenActivity")

        val intent = Intent(activity, FullscreenActivity::class.java).apply {
            putExtra(EXTRA_URL, flicckrPhoto.url)
            putExtra(EXTRA_TITLE, flicckrPhoto.title)
        }
        startActivity(intent)

    }

    fun photoItemClicked(flickrPhoto:FlickrPhoto?){

        Log.d("Igor Log","SearcherFragment.photoItemClicked")


        flickrPhoto?.let {startFullscreenActivity(flickrPhoto)}

     }


}
