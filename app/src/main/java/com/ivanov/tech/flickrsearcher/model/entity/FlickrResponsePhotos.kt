package com.ivanov.tech.flickrsearcher.model.entity

import com.squareup.moshi.Json

class FlickrResponsePhotos {
    @field:Json(name = "page") var page: Int? = null
    @field:Json(name = "pages") var pages: Int? = null
    @field:Json(name = "perpage") var perpage: Int? = null
    @field:Json(name = "total") var total: String? = null
    @field:Json(name = "photo") lateinit var photo: List<FlickrPhoto>
}
