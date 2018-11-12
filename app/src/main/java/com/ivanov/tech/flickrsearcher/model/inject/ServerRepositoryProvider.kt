package com.ivanov.tech.flickrsearcher.model.inject

import com.ivanov.tech.flickrsearcher.model.repository.ServerRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider


class ServerMethodsProvider : Provider<ServerRepository> {

    override fun get(): ServerRepository {
        val retrofit = Retrofit.Builder()
                .baseUrl(ServerSettings.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(ServerRepository::class.java)
    }

}

object ServerSettings{
    @JvmStatic public val BASE_URL = "https://api.flickr.com/"
    @JvmStatic public val KEY = "591f6080ae91145299f619fca69d9245"
}
