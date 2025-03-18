package com.example.galleryapp.utils

import android.app.Activity
import com.example.adsdk.data.sources.AdManager
import com.example.adsdk.domain.models.AdState
import kotlinx.coroutines.flow.Flow

class AdController(
    private val adManager: AdManager,
    private val appLaunchTracker: AppLaunchTracker
) : IAdController {

    private var adPositions: List<Int> = listOf() // Default positions
    private var isNativeAdEnabled: Boolean = true
    private var isInterstitialAdEnabled: Boolean = true

    override fun setNativeAdEnabled(enabled: Boolean) {
        isNativeAdEnabled = enabled
    }

    override fun setInterstitialAdEnabled(enabled: Boolean) {
        isInterstitialAdEnabled = enabled
    }

    override fun setAdPositions(positions: List<Int>) {
        adPositions = positions
    }

    override fun shouldShowNativeAd(index: Int): Boolean {
        return isNativeAdEnabled && adPositions.contains(index)
    }

    override fun shouldShowInterstitialAd(): Boolean {
        return isInterstitialAdEnabled && appLaunchTracker.shouldShowInterstitialAd()
    }

    override fun shouldShowNativeAd(): Boolean = isNativeAdEnabled

    override fun getInterstitialAdState(): Flow<AdState> {
        return adManager.getInterstitialAdState()
    }

    override fun getNativeAdState(): Flow<AdState> {
        return adManager.getNativeAdState()
    }

    override fun loadInterstitialAd() {
        adManager.loadInterstitialAd()
    }

    override fun showInterstitialAd(activity: Activity) {
        adManager.showInterstitialAd(activity)
    }

    override fun loadNativeAd() {
        adManager.loadNativeAd()
    }

    override fun incrementLaunchCount() {
        appLaunchTracker.incrementLaunchCount()
    }
}
