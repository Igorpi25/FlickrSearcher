package com.ivanov.tech.flickrsearcher

import android.arch.lifecycle.LiveData

interface SuggestionsRepository {
    fun getList():LiveData<List<String>> //Get list of string suggestionsList as LiveData
    fun put(value:String) //Put new searchedText value to suggestionsList repository
    fun clear() //Remove suggestionsList
}