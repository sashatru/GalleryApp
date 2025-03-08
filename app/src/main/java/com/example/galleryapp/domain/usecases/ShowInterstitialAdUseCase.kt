package com.example.galleryapp.domain.usecases

import android.app.Activity
import com.example.adsdk.data.sources.AdManager

class ShowInterstitialAdUseCase(private val adManager: AdManager) {
    fun execute(activity: Activity) {
        adManager.showInterstitialAd(activity)
    }
}
