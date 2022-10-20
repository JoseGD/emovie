package com.example.emovie.api

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by josegonzalezdamico on 19/10/2022
 */

class RetrofitManager: KoinComponent {

    private val retrofit: Retrofit
    private val gson: Gson = get()

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        retrofit = okHttpClientBuilder.buildRetrofit()
    }

    fun createService(): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    private fun OkHttpClient.Builder.buildRetrofit() =
        Retrofit.Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            // This auto creates the calls on a specific scheduler
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .callFactory(build())
            .build()

    companion object {
        const val API_URL = "https://api.themoviedb.org/3/"
    }

}
