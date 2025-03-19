package com.example.galleryapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adsdk.domain.models.AdState
import com.example.galleryapp.utils.IAdController
import com.example.galleryapp.domain.models.ContentElement
import com.example.galleryapp.domain.usecases.GetContentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val adController: IAdController,
    private val getContentUseCase: GetContentUseCase
) : ViewModel() {

    private val _shouldShowInterstitialAd = MutableStateFlow(false)
    val shouldShowInterstitialAd: StateFlow<Boolean> = _shouldShowInterstitialAd

    private val _nativeAdState = MutableStateFlow<AdState>(AdState.Loading)
    val nativeAdState: StateFlow<AdState> = _nativeAdState

    private val _contentList = MutableStateFlow<List<ContentElement>>(emptyList())
    val contentList: StateFlow<List<ContentElement>> = _contentList

    init {
        configureAdPositions()
        checkInterstitialAdCondition()
        checkNativeAdConditions()
        loadContent()
    }

    private fun configureAdPositions() {
        val adPositions = listOf(1, 3) // Example positions
        adController.setAdPositions(adPositions)
    }

    private fun checkInterstitialAdCondition() {
        viewModelScope.launch {
            _shouldShowInterstitialAd.value = adController.shouldShowInterstitialAd()
        }
    }

    private fun checkNativeAdConditions() {
        if (adController.shouldShowNativeAd()) {
            observeNativeAdState()
        }
    }

    private fun loadContent() {
        viewModelScope.launch {
            _contentList.value = getContentUseCase()
        }
    }

    private fun observeNativeAdState() {
        viewModelScope.launch {
            adController.getNativeAdState().collect { state ->
                _nativeAdState.value = state
            }
        }
    }

    fun adShown() {
        _shouldShowInterstitialAd.value = false
    }
}
