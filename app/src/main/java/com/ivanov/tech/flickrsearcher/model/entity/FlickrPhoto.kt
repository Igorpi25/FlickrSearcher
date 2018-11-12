package com.ivanov.tech.flickrsearcher.model.entity

import com.squareup.moshi.Json


class FlickrPhoto {
    @field:Json(name = "id") var id: String? = null
    @field:Json(name = "owner") var owner: String? = null
    @field:Json(name = "secret") var secret: String? = null
    @field:Json(name = "server") var server: String? = null
    @field:Json(name = "farm") var farm: Int? = null
    @field:Json(name = "title") var title: String? = null
    @field:Json(name = "ispublic") var ispublic: Int? = null
    @field:Json(name = "isfriend") var isfriend: Int? = null
    @field:Json(name = "isfamily") var isfamily: Int? = null

    val url: String
        get() {
            val farm = farm!!
            val server = server
            val id = id
            val secret = secret

            return url("m")
        }

    fun url(imageSizeSuff:String):String{
        return ("http://farm" + farm + ".static.flickr.com/"
                + server + "/" + id + "_" + secret + "_"+imageSizeSuff+".jpg")
    }
}
