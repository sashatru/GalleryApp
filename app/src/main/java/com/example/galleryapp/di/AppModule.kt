package com.example.galleryapp.di

import com.example.adsdk.data.sources.AdManager
import com.example.galleryapp.data.local.LocalGalleryDataSource
import com.example.galleryapp.domain.repository.GalleryDataSource
import com.example.galleryapp.domain.repository.GalleryRepository
import com.example.galleryapp.domain.usecases.GetContentUseCase
import com.example.galleryapp.domain.usecases.ShowInterstitialAdUseCase
import com.example.galleryapp.presentation.MainViewModel
import com.example.galleryapp.utils.AdController
import com.example.galleryapp.utils.AppLaunchTracker
import com.example.galleryapp.utils.IAdController
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppLaunchTracker(androidContext()) }
    single { AdManager(androidContext()) }
    single<IAdController> { AdController(get(), get()) }
    factory { ShowInterstitialAdUseCase(get()) }
    single<GalleryDataSource> { LocalGalleryDataSource() }
    single { GalleryRepository(get()) }
    single { GetContentUseCase(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
}
