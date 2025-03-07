package com.example.galleryapp.presentation

import android.app.Activity
import android.util.Log
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
    private val appLaunchTracker: AppLaunchTracker
) : ViewModel() {

    private val _shouldShowInterstitialAd = MutableStateFlow(false)
    val shouldShowInterstitialAd: StateFlow<Boolean> = _shouldShowInterstitialAd

    init {
        checkInterstitialAdCondition()
    }

    private fun checkInterstitialAdCondition() {
        viewModelScope.launch {
            _shouldShowInterstitialAd.value = appLaunchTracker.shouldShowInterstitialAd()
        }
    }

    fun adShown() {
        _shouldShowInterstitialAd.value = false
    }
}
