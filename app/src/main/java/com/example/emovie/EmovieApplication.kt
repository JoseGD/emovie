package com.example.emovie

import android.app.Application
import com.example.emovie.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by josegonzalezdamico on 19/10/2022
 */

class EmovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EmovieApplication)
            modules(koinModule)
        }
    }

}
