package com.example.galleryapp

import android.app.Application
import com.example.galleryapp.di.appModule
import com.example.galleryapp.utils.AppLaunchTracker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GalleryApp : Application(), KoinComponent {
    private val appLaunchTracker: AppLaunchTracker by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GalleryApp)
            modules(appModule)
        }

        appLaunchTracker.incrementLaunchCount()
    }
}
