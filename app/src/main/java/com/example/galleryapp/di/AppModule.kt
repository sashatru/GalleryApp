package com.example.galleryapp.di

import com.example.adsdk.AdManager
import org.koin.dsl.module

val appModule = module {
    single { AdManager(get()) }
}
