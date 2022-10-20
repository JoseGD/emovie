package com.example.emovie.di

import com.example.emovie.api.RetrofitManager
import com.google.gson.GsonBuilder
import org.koin.dsl.module

/**
 * Created by josegonzalezdamico on 19/10/2022
 */

val koinModule = module {

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }
    single { RetrofitManager() }

}
