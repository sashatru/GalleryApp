package com.example.galleryapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsdk.data.sources.AdManager
import com.example.adsdk.domain.models.AdState
import com.example.galleryapp.domain.usecases.GetImagesUseCase
import com.example.galleryapp.utils.AppLaunchTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val adManager: AdManager,
    private val appLaunchTracker: AppLaunchTracker,
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _shouldShowInterstitialAd = MutableStateFlow(false)
    val shouldShowInterstitialAd: StateFlow<Boolean> = _shouldShowInterstitialAd

    private val _nativeAdState = MutableStateFlow<AdState>(AdState.Loading)
    val nativeAdState: StateFlow<AdState> = _nativeAdState

    private val _imageList = MutableStateFlow<List<Int>>(emptyList())
    val imageList: StateFlow<List<Int>> = _imageList

    init {
        checkInterstitialAdCondition()
        observeNativeAdState()
        loadImages()
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

    private fun loadImages() {
        viewModelScope.launch {
            _imageList.value = getImagesUseCase()
        }
    }

    fun adShown() {
        _shouldShowInterstitialAd.value = false
    }
}
