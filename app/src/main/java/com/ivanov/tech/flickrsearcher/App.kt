package com.ivanov.tech.flickrsearcher

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


const val EXTRA_URL = "com.ivanov.tech.flickrsearcher.URL"
const val EXTRA_TITLE = "com.ivanov.tech.flickrsearcher.TITLE"


val prefs: Prefs by lazy {
    App.prefs
}

class App : Application() {
    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}

class Prefs (context: Context) {
    val PREFS_FILENAME = "com.ivanov.tech.flickrsearcher.prefs"
    val SUGGESTIONS = "suggestions"
    val LAST_SEARCHED_TEXT = "last_searched_text"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var suggestions: List<String>
        get() {
            val json_string=prefs.getString(SUGGESTIONS,"[]")

            val moshi = Moshi.Builder()
                    .build()

            val type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter : JsonAdapter<List<String>> = moshi.adapter(type)
            val list = adapter.fromJson(json_string)?: arrayListOf()

            return list
        }

        set(list){

            val moshi = Moshi.Builder()
                    .build()

            val type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter : JsonAdapter<List<String>> = moshi.adapter(type)
            val json_string = adapter.toJson(list)

            val editor = prefs.edit()
            editor.putString(SUGGESTIONS, json_string)
            editor.apply()
        }

    var last_searched_text: String
        get() = prefs.getString(LAST_SEARCHED_TEXT,"")
        set(text){
            val editor = prefs.edit()
            editor.putString(LAST_SEARCHED_TEXT, text)
            editor.apply()
        }
}