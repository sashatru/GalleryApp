package com.example.galleryapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsdk.domain.models.AdState
import com.example.galleryapp.domain.usecases.GetImagesUseCase
import com.example.galleryapp.utils.IAdController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val adController: IAdController,
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
        checkNativeAdConditions()
        loadImages()
    }

    private fun checkInterstitialAdCondition() {
        viewModelScope.launch {
            _shouldShowInterstitialAd.value = adController.shouldShowInterstitialAd()
        }
    }

    private fun checkNativeAdConditions() {
        if (adController.shouldShowNativeAd()) {
            observeNativeAdState()
            configureAdPositions()
        }
    }

    private fun loadImages() {
        viewModelScope.launch {
            _imageList.value = getImagesUseCase()
        }
    }

    private fun observeNativeAdState() {
        viewModelScope.launch {
            adController.getNativeAdState().collect { state ->
                _nativeAdState.value = state
            }
        }
    }

    private fun configureAdPositions() {
        val adPositions: List<Int> = listOf(1, 3) // Example positions
        adController.setAdPositions(adPositions)
    }

    fun shouldShowNativeAd(index: Int): Boolean {
        return adController.shouldShowNativeAd(index)
    }

    fun adShown() {
        _shouldShowInterstitialAd.value = false
    }
}
