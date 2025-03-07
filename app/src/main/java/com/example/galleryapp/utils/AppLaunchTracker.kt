package com.example.galleryapp.utils

import android.content.Context

class AppLaunchTracker(context: Context) {
    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "app_prefs"
        private const val KEY_LAUNCH_COUNT = "launch_count"
    }

    fun shouldShowInterstitialAd(): Boolean {
        return prefs.getInt(KEY_LAUNCH_COUNT, 0) > 1
    }

    fun incrementLaunchCount() {
        val launchCount = prefs.getInt(KEY_LAUNCH_COUNT, 0)
        prefs.edit().putInt(KEY_LAUNCH_COUNT, launchCount + 1).apply()
    }
}
