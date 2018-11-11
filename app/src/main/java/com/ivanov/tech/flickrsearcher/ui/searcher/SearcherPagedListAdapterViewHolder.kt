package com.ivanov.tech.flickrsearcher.ui.searcher

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ivanov.tech.flickrsearcher.R
import com.ivanov.tech.flickrsearcher.server.FlickrPhoto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*

class SearcherPagedListAdapterViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    var flickrPhoto : FlickrPhoto? = null

    fun bind(photo: FlickrPhoto?) {

        flickrPhoto=photo

        if(flickrPhoto!=null) {

            Picasso.get().load(flickrPhoto?.url).into(itemView.photoImageView as ImageView);

            (itemView.titleTextView as TextView).text = flickrPhoto?.title

        }
    }

    companion object {
        fun create(parent: ViewGroup, callback:(FlickrPhoto?)->Unit): SearcherPagedListAdapterViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo, parent, false)

            val holder=SearcherPagedListAdapterViewHolder(view)
            view.photoItemCardView.setOnClickListener({callback(holder.flickrPhoto)})

            return holder
        }
    }
}