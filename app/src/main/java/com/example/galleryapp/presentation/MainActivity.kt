package com.example.galleryapp.presentation

import GalleryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adsdk.AdManager
import com.example.galleryapp.utils.AppLaunchTracker
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val adManager: AdManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryScreen(adManager)
        }

        // Show interstitial ad only on the second app launch
        mainViewModel.checkAndShowInterstitialAd(this)
    }
}
