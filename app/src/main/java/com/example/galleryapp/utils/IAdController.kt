package com.example.galleryapp.utils

import android.app.Activity
import com.example.adsdk.domain.models.AdState
import kotlinx.coroutines.flow.Flow

interface IAdController {
    fun setNativeAdEnabled(enabled: Boolean)
    fun setInterstitialAdEnabled(enabled: Boolean)
    fun setAdPositions(positions: List<Int>)
    fun getAdPositions(): List<Int>
    fun shouldShowNativeAd(index: Int): Boolean
    fun shouldShowInterstitialAd(): Boolean
    fun shouldShowNativeAd(): Boolean
    fun getInterstitialAdState(): Flow<AdState>
    fun getNativeAdState(): Flow<AdState>
    fun loadInterstitialAd()
    fun showInterstitialAd(activity: Activity)
    fun loadNativeAd()
    fun incrementLaunchCount()
}
