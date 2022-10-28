package com.example.emovie.di

import com.example.emovie.api.RetrofitManager
import com.example.emovie.home.HomeViewModel
import com.example.emovie.repositories.MoviesRepository
import com.example.emovie.repositories.MoviesRepositoryImpl
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by josegonzalezdamico on 19/10/2022
 */

val koinModule = module {
    single {
        GsonBuilder().setLenient()
                     .create()
    }
    single { RetrofitManager() }
    single<MoviesRepository> { MoviesRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { HomeViewModel() }
}
