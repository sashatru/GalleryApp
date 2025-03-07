package com.example.galleryapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsdk.AdManager
import com.example.adsdk.AdState
import com.example.galleryapp.utils.AppLaunchTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class MainViewModel(
    private val adManager: AdManager,
    private val appLaunchTracker: AppLaunchTracker
) : ViewModel() {

    private val _shouldShowInterstitialAd = MutableStateFlow(false)
    val shouldShowInterstitialAd: StateFlow<Boolean> = _shouldShowInterstitialAd

    private val _nativeAdState = MutableStateFlow<AdState>(AdState.Loading)
    open val nativeAdState: StateFlow<AdState> = _nativeAdState

    init {
        checkInterstitialAdCondition()
        observeNativeAdState()
    }

    private fun checkInterstitialAdCondition() {
        viewModelScope.launch {
            _shouldShowInterstitialAd.value = appLaunchTracker.shouldShowInterstitialAd()
        }
    }

    private fun observeNativeAdState() {
        viewModelScope.launch {
            adManager.getNativeAdState().collect { state ->
                _nativeAdState.value = state
            }
        }
    }

    fun adShown() {
        _shouldShowInterstitialAd.value = false
    }
}
