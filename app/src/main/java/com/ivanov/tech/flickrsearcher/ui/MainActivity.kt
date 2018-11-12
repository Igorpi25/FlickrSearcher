package com.ivanov.tech.flickrsearcher.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ivanov.tech.flickrsearcher.R
import com.ivanov.tech.flickrsearcher.ui.searcher.SearcherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SearcherFragment.newInstance())
                    .commitNow()
        }

    }

}
