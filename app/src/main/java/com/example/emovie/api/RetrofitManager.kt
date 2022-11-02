package com.example.emovie.api

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
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
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(interceptor())
        retrofit = buildRetrofit(okHttpClient.build())
    }

    fun createService(): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            // This auto creates the calls on a specific scheduler
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()
    }

    private fun interceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY_Q_PARAM, API_KEY)
                        .build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    companion object {
        const val API_URL = "https://api.themoviedb.org/3/"
        const val API_KEY_Q_PARAM = "api_key"
        const val API_KEY = "d3f30e957b7de4d34592cef5a75b8e0b"
    }

}
