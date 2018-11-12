package com.ivanov.tech.flickrsearcher.model.inject

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.util.Log
import com.ivanov.tech.flickrsearcher.model.repository.SuggestionsRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import toothpick.Toothpick
import javax.inject.Inject
import javax.inject.Provider

class SuggestionsRepositoryProvider:Provider<SuggestionsRepository>{

    @Inject
    lateinit var prefs:SharedPreferences

    init{

        Log.e("Igor log","SuggestionsRepositoryProvider init")

        val appScope = Toothpick.openScopes("AppScope");
        Toothpick.inject(this, appScope);

    }

    override fun get(): SuggestionsRepository {
        return repository(prefs)
    }

    private class repository(private val prefs:SharedPreferences): SuggestionsRepository {

        val livedata= MutableLiveData<List<String>>()


        private val SUGGESTIONS = "suggestions"
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

            if(livedata.value==null){

                livedata.postValue(suggestions)
            }


            return livedata
        }

        override fun put(value: String) {

            val temp_suggestions=suggestions as ArrayList

            if(!temp_suggestions.contains(value)) {
                Log.e("Igor log","repository.put.notContain(${value}) then add")
                temp_suggestions.add(value)
                suggestions = temp_suggestions

                livedata.postValue(suggestions)
            }else{
                Log.e("Igor log","repository.put.Contain(${value}) then skip")
            }

        }

        override fun clear() {
            suggestions= emptyList()
            livedata.postValue(suggestions)
        }

    }

}