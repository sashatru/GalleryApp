package com.example.galleryapp

import android.app.Application
import com.example.galleryapp.di.appModule
import org.koin.core.context.startKoin

class GalleryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}
