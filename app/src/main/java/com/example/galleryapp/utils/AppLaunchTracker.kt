package com.example.galleryapp.utils

import android.content.Context

object AppLaunchTracker {
    private const val PREF_NAME = "app_prefs"
    private const val KEY_LAUNCH_COUNT = "launch_count"

    fun shouldShowInterstitialAd(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val launchCount = prefs.getInt(KEY_LAUNCH_COUNT, 0)

        prefs.edit().putInt(KEY_LAUNCH_COUNT, launchCount + 1).apply()

        return launchCount >= 1
    }
}
