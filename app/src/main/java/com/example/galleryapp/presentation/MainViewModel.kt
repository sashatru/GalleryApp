package com.example.galleryapp.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsdk.AdManager
import com.example.adsdk.AdState
import com.example.galleryapp.domain.usecases.ShowInterstitialAdUseCase
import com.example.galleryapp.utils.AppLaunchTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val adManager: AdManager,
    private val appLaunchTracker: AppLaunchTracker,
    private val showInterstitialAdUseCase: ShowInterstitialAdUseCase
) : ViewModel() {
    private val _interstitialAdState = MutableStateFlow<AdState>(AdState.Loading)
    val interstitialAdState: StateFlow<AdState> = _interstitialAdState

    /*
        init {
            loadInterstitialAd()
        }

        fun loadInterstitialAd() {
            viewModelScope.launch {
                adManager.loadInterstitialAd()
            }
        }
    */

    fun checkAndShowInterstitialAd(activity: Activity) {
        if (appLaunchTracker.shouldShowInterstitialAd()) {
            viewModelScope.launch {
                showInterstitialAdUseCase.execute(activity)
            }
        }
    }
}
