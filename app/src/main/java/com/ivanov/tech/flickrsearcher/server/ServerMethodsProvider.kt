package com.ivanov.tech.flickrsearcher.server

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider


class ServerMethodsProvider : Provider<ServerMethods> {

    override fun get(): ServerMethods {
        val retrofit = Retrofit.Builder()
                .baseUrl(ServerSettings.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(ServerMethods::class.java)
    }

}
