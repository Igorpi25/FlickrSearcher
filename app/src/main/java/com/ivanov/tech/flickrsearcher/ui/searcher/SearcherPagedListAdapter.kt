package com.ivanov.tech.flickrsearcher.ui.searcher

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto

class SearcherPagedListAdapter(val callback:(FlickrPhoto?)->Unit)
    : PagedListAdapter<FlickrPhoto, SearcherPagedListAdapterViewHolder>(diffUtilItemCallback) {

    override fun onBindViewHolder(holderPagedListedAdapter: SearcherPagedListAdapterViewHolder, position: Int) {
        holderPagedListedAdapter.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearcherPagedListAdapterViewHolder {
        return SearcherPagedListAdapterViewHolder.create(parent,callback)
    }

    companion object {

        val diffUtilItemCallback = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}