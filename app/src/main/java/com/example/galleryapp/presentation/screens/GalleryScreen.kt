package com.example.galleryapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.adsdk.AdState
import com.example.galleryapp.R
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun GalleryScreen(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    val nativeAdState by mainViewModel.nativeAdState.collectAsState()
    val context = LocalContext.current

    val imageList = listOf(
        R.drawable.ab1_inversions,
        R.drawable.ab2_quick_yoga,
        R.drawable.ab3_stretching,
        R.drawable.ab4_tabata,
        R.drawable.ab5_hiit,
        R.drawable.ab6_pre_natal_yoga
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(imageList) { index, imageRes ->
            if (index == 1 || index == 3) {
                when (val state = nativeAdState) {
                    is AdState.NativeAdLoaded -> {
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            factory = { ctx ->
                                val inflater = LayoutInflater.from(ctx)
                                val adView = inflater.inflate(R.layout.native_ad_layout, null) as NativeAdView
                                populateNativeAdView(state.nativeAd, adView)
                                adView
                            }
                        )
                    }
                    is AdState.Failed -> Text("Ad failed to load")
                    else -> Text("Loading ad...")
                }
            } else {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Gallery Image $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)

    (adView.headlineView as TextView).text = nativeAd.headline
    (adView.bodyView as? TextView)?.text = nativeAd.body
    (adView.callToActionView as? Button)?.apply {
        text = nativeAd.callToAction
        setOnClickListener { nativeAd.performClick(Bundle()) }
    }

    nativeAd.icon?.drawable?.let { icon ->
        (adView.iconView as? ImageView)?.setImageDrawable(icon)
    }

    adView.setNativeAd(nativeAd)
}
