package com.ivanov.tech.flickrsearcher.model.entity

import com.squareup.moshi.Json

class FlickrResponse {

    @field:Json(name = "photos") var photos: FlickrResponsePhotos? = null
    @field:Json(name = "stat") var stat: String? = null
    @field:Json(name = "message") var message: String? = null
    @field:Json(name = "code") var code: String? = null
}
