package com.example.galleryapp.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.adsdk.domain.models.AdState
import com.example.galleryapp.R
import com.example.galleryapp.presentation.MainViewModel
import com.example.galleryapp.ui.theme.GalleryAppTheme
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun GalleryScreen(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    val nativeAdState by mainViewModel.nativeAdState.collectAsState()
    val imageList by mainViewModel.imageList.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(imageList) { index, imageRes ->
            ImageWithAd(index = index, imageRes = imageRes, nativeAdState = nativeAdState)
        }
    }
}

@Composable
fun ImageWithAd(index: Int, imageRes: Int, nativeAdState: AdState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (index == 1 || index == 3) {
            when (nativeAdState) {
                is AdState.NativeAdLoaded -> {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { ctx ->
                                val inflater = LayoutInflater.from(ctx)
                                val parent = androidx.constraintlayout.widget.ConstraintLayout(ctx)
                                val adView = inflater.inflate(
                                    R.layout.native_ad_layout,
                                    parent,
                                    false
                                ) as NativeAdView
                                populateNativeAdView(nativeAdState.nativeAd, adView)
                                adView
                            }
                        )
                    }
                }

                is AdState.Failed -> AdErrorCard()
                else -> AdLoadingCard()
            }
        }

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

@Composable
fun AdLoadingCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = Color.Blue,
                strokeWidth = 3.dp,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
            Text(
                text = stringResource(R.string.loading_ad),
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun AdErrorCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "Error icon",
                tint = Color.Red,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.failed_to_load_ad),
                fontSize = 16.sp,
                color = Color.Red,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    GalleryAppTheme {
        AdLoadingCard()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    GalleryAppTheme {
        AdErrorCard()
    }
}

