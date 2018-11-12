package com.ivanov.tech.flickrsearcher

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import toothpick.Toothpick
import javax.inject.Provider

class SuggestionsRepositoryProvider(application: Application):Provider<SuggestionsRepository>{

    //@Inject
    //private lateinit var prefs:SharedPreferences

    val prefs = application.getSharedPreferences("com.ivanov.tech.flickrsearcher.prefs",0)

    init{

        Log.e("Igor log","SuggestionsRepositoryProvider init")

        val appScope = Toothpick.openScope("AppScope");
        Toothpick.inject(this, appScope);

    }

    override fun get(): SuggestionsRepository {
        return repository(prefs)
    }

    private class repository(val prefs:SharedPreferences): SuggestionsRepository{

        val livedata= MutableLiveData<List<String>>()

        init{
            Log.e("Igor log","SuggestionsRepositoryProvider.repository init")
            Log.e("Igor log","suggestions="+suggestions.toString())

            livedata.postValue(suggestions)
        }

        private val SUGGESTIONS = "suggestionsList"
        private var suggestions: List<String>
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

        override fun getList(): LiveData<List<String>> {
            return livedata
        }

        override fun put(value: String) {

            val temp_suggestions=suggestions as ArrayList

            if(!temp_suggestions.contains(value)) {

                temp_suggestions.add(value)
                suggestions = temp_suggestions

                livedata.postValue(suggestions)
            }
        }

        override fun clear() {
            suggestions= emptyList()
            livedata.postValue(suggestions)
        }

    }

}