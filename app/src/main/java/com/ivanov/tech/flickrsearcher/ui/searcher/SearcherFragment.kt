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
import com.ivanov.tech.flickrsearcher.R
import kotterknife.bindView
import com.ivanov.tech.flickrsearcher.App.Companion.prefs
import com.ivanov.tech.flickrsearcher.EXTRA_TITLE
import com.ivanov.tech.flickrsearcher.EXTRA_URL
import com.ivanov.tech.flickrsearcher.FullscreenActivity
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import io.reactivex.Single
import java.util.*


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
        val view=inflater.inflate(R.layout.fragment_searcher, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearcherViewModel::class.java)
        // TODO: Use the ViewModel
        createAdapter()
        setupAutoCompleteEditText()
    }

    private fun createAdapter() {
        searcherPagedListAdapter = SearcherPagedListAdapter(::photoItemClicked)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.adapter = searcherPagedListAdapter


        viewModel.photoPagedList.observe(this, Observer {
            searcherPagedListAdapter.submitList(it)
        })

    }

    private fun setupAutoCompleteEditText(){

        val suggestions=prefs.suggestions as ArrayList<String>

        val adapter=ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, suggestions)

        // Create the adapter and set it to the AutoCompleteTextView
        search.setAdapter(adapter)

        search.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == KeyEvent.KEYCODE_ENTER){

                Log.e("Igor log","Add text to list of suggestion")

                val text=search.text.toString()
                adapter.add(text)

                adapter.clear()
                adapter.addAll(suggestions)


                suggestions.add(text)
                prefs.suggestions = suggestions

                searchAction(text)

                true
            }else{
                false
            }
        }
    }

    fun searchAction(text:String){
        viewModel.photoPagedList.value?.clear()
        searcherPagedListAdapter.notifyDataSetChanged()

    }

    fun startFullscreenActivity(flicckrPhoto: FlickrPhoto){
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
