package com.example.galleryapp.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.adsdk.data.sources.AdManager
import com.example.galleryapp.domain.usecases.ShowInterstitialAdUseCase
import com.example.galleryapp.presentation.screens.GalleryScreen
import com.example.galleryapp.ui.theme.GalleryAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val adManager: AdManager by inject()
    private val showInterstitialAdUseCase: ShowInterstitialAdUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GalleryScreen(
                        mainViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // Show interstitial ad only on the second app launch
        lifecycleScope.launch {
            mainViewModel.shouldShowInterstitialAd.collectLatest { shouldShow ->
                if (shouldShow) {
                    showInterstitialAdUseCase.execute(this@MainActivity)
                    mainViewModel.adShown()
                }
            }
        }
    }
}
