package com.example.galleryapp.di

import com.example.adsdk.AdManager
import com.example.galleryapp.presentation.MainViewModel
import com.example.galleryapp.utils.AppLaunchTracker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppLaunchTracker(androidContext()) }
    single { AdManager(androidContext()) }
    viewModel { MainViewModel(get(), get()) }
}
